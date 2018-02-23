/*
 * Copyright (c) 2018 SilkSource.
 */
package io.silksource.silk.testing.coverage;

import static io.silksource.silk.testdata.FullyQualifiedNameBuilder.someFullyQualifiedName;
import static io.silksource.silk.testdata.IdentifierBuilder.someIdentifier;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Method;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;

import org.apache.commons.io.IOUtils;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import io.silksource.silk.coding.api.FullyQualifiedName;
import io.silksource.silk.coding.api.Identifier;
import io.silksource.silk.coding.api.Project;
import io.silksource.silk.coding.api.SourceSetName;
import io.silksource.silk.coding.api.Type;
import io.silksource.silk.coding.event.TypeCompiledEvent;
import io.silksource.silk.testdata.ProjectBuilder;
import io.silksource.silk.testing.InMemoryClassLoader;


public abstract class WhenInstrumentingCodeForCoverageAnalysis {

  protected abstract CodeInstrumenter newCodeInstrumenter();

  @Rule
  public TemporaryFolder temporaryFolder = new TemporaryFolder();
  private CodeInstrumenter codeInstrumenter;

  private final FullyQualifiedName typeName = someFullyQualifiedName();
  private final Identifier methodName = someIdentifier();
  private Project project;

  @Before
  public void init() {
    codeInstrumenter = newCodeInstrumenter();
  }

  @Test
  public void shouldCreateInstrumentedVersionsOfCompiledSources() {
    buildProject();

    installPlugin();

    assertTypeIsInstrumented();
  }

  private void buildProject() {
    project = ProjectBuilder.inDirectory(temporaryFolder.getRoot())
        .withType(typeName)
            .withMethod(methodName)
            .end()
        .end()
        .build();
  }

  private void installPlugin() {
    codeInstrumenter.installIn(project);
  }

  private File assertTypeIsInstrumented() {
    File result = new File(String.format("%s/instrumented/%s/%s.class",
        project.getScrathPath(), SourceSetName.MAIN.id(), typeName.getInternalName()));
    assertTrue("Instrumented file not created: " + result, result.isFile());

    return result;
  }

  @Test(expected = FailedToInstrumentTypeException.class)
  public void shouldThrowExceptionWhenInstrumentationFails() {
    codeInstrumenter = new CodeInstrumenter() {
      @Override
      protected void instrument(String name, InputStream input, OutputStream output) throws IOException {
        throw new IOException();
      }
    };
    buildProject();

    installPlugin();
  }

  @Test
  public void shouldReinstrumentCodeWhenItIsCompiled() {
    buildProject();
    Type type = getType();
    installPlugin();
    long creationTime = System.currentTimeMillis();

    project.fire(new TypeCompiledEvent(type));

    long modificationTime = assertTypeIsInstrumented().lastModified();
    assertTrue("Instrumented file not updated", modificationTime > creationTime);
  }

  private Type getType() throws AssertionError {
    return project.mainSources().type(typeName)
        .orElseThrow(() -> new AssertionError("Type not found"));
  }

  @Test
  @Ignore("TODO: Figure out why this doesn't work")
  public void shouldBeAbleToRunInstrumentedCode() throws ReflectiveOperationException, IOException {
    buildProject();
    assertCanCallMethod("original", getType().getCompiledPath().toFile());

    installPlugin();

    assertCanCallMethod("instrumented", assertTypeIsInstrumented());
  }

  private void assertCanCallMethod(String type, File typeFile) throws ReflectiveOperationException, IOException {
    String message = String.format("Can't call %s method", type);
    Class<?> typeClass = loadClass(getType(), typeFile);
    Method method = typeClass.getMethod(methodName.toString());
    Object instance = newInstance(message, typeClass);
    assertNull(message, method.invoke(instance));
  }

  private Class<?> loadClass(Type type, File classFile) throws ReflectiveOperationException, IOException {
    try (InputStream input = Files.newInputStream(classFile.toPath(), StandardOpenOption.READ)) {
      return loadClass(type, input);
    }
  }

  private Class<?> loadClass(Type type, InputStream input) throws ReflectiveOperationException, IOException {
    InMemoryClassLoader memoryClassLoader = new InMemoryClassLoader();
    String className = type.getName().toString();
    memoryClassLoader.addDefinition(className, IOUtils.toByteArray(input));
    return memoryClassLoader.loadClass(className);
  }

  private Object newInstance(String message, Class<?> type) throws ReflectiveOperationException {
    try {
      return type.newInstance();
    } catch (RuntimeException e) {
      throw new AssertionError(message + " - Exception during instantiation of type", e);
    }
  }

}
