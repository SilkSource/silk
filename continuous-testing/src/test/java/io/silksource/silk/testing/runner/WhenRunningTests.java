/*
 * Copyright (c) 2018 SilkSource.
 */
package io.silksource.silk.testing.runner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;
import java.util.Optional;
import java.util.Set;

import org.apache.commons.io.IOUtils;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;
import org.mockito.ArgumentCaptor;

import io.silksource.silk.coding.api.FullyQualifiedName;
import io.silksource.silk.coding.api.Identifier;
import io.silksource.silk.coding.api.Method;
import io.silksource.silk.coding.api.Project;
import io.silksource.silk.coding.api.Type;
import io.silksource.silk.testdata.ProjectBuilder;


public abstract class WhenRunningTests {

  @Rule
  public TemporaryFolder temporaryFolder = new TemporaryFolder();
  private final FullyQualifiedName typeName = FullyQualifiedName.parse(getClass().getName());
  private final Identifier methodName = new Identifier("shouldDiscoverTests");
  private final Identifier ignoredMethodName = new Identifier("skipped");
  private Project project;
  private TestRunner testRunner;

  protected abstract TestRunner newTestRunner();

  @Before
  public void init() throws IOException {
    testRunner = newTestRunner();
    createProjectWithCopyOfCurrentTestClass();
  }

  private void createProjectWithCopyOfCurrentTestClass() throws IOException {
    project = ProjectBuilder.inDirectory(temporaryFolder.getRoot())
        .withTestType(typeName)
            .withTestMethod(methodName)
            .end()
            .withTestMethod(ignoredMethodName)
                .withAnnotation("org.junit.Ignore")
            .end()
        .end()
    .build();
    Type type = project.testSources()
        .type(typeName)
        .orElseThrow(() -> new AssertionError("Missing type"));
    try (InputStream input = loadClassBytes()) {
      try (OutputStream output = Files.newOutputStream(type.getCompiledPath(), StandardOpenOption.CREATE)) {
        IOUtils.copy(input, output);
      }
    }
  }

  private InputStream loadClassBytes() {
    return getClass().getResourceAsStream(String.format("/%s.class", typeName.getInternalName()));
  }

  @Test
  public void shouldDiscoverTests() {
    Set<Method> discoveredTestMethods = testRunner.findTestMethodsIn(project);

    assertTrue("Test method is not discovered by runner", discoveredTestMethods.stream()
        .filter(this::isTestMethod)
        .findAny()
        .isPresent());
  }

  private boolean isTestMethod(Method method) {
    return methodName.equals(method.getName()) && typeName.equals(method.getOwningType().getName());
  }

  @Test
  public void shouldRunTest() {
    Method testMethod = testMethodNamed(methodName);

    TestListener listener = mock(TestListener.class);
    testRunner.runTest(testMethod, listener);

    verify(listener).start(typeName, testMethod.getName());
    ArgumentCaptor<TestResult> resultCaptor = ArgumentCaptor.forClass(TestResult.class);
    verify(listener).finish(eq(typeName), eq(testMethod.getName()),
        resultCaptor.capture());
    TestResult result = resultCaptor.getValue();
    assertEquals("Status", TestResult.Status.PASSED, result.getStatus());
    assertFalse("Exception", result.getException().isPresent());
  }

  private Method testMethodNamed(Identifier name) {
    Optional<Type> type = project.testSources().type(typeName);
    assertTrue("Missing type", type.isPresent());

    Optional<Method> method = type.get().method(name);
    assertTrue(String.format("Missing method %s in type %s", name, typeName), method.isPresent());

    return method.get();
  }

  @Test
  public void shouldSkipIgnoredTest() {
    Method testMethod = testMethodNamed(ignoredMethodName);

    TestListener listener = mock(TestListener.class);
    testRunner.runTest(testMethod, listener);

    verify(listener).skip(typeName, testMethod.getName());
  }

  @Ignore("Referenced in test that verifies test methods can be skipped")
  @Test
  public void skipped() {
    assertEquals(2L, 1 + 1);
  }

}
