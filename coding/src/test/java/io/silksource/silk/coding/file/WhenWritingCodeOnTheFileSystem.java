/*
 * Copyright (c) 2018 SilkSource.
 */
package io.silksource.silk.coding.file;

import static org.junit.Assert.assertTrue;

import java.io.File;
import java.util.Optional;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import io.silksource.silk.coding.api.FullyQualifiedName;
import io.silksource.silk.coding.api.Identifier;
import io.silksource.silk.coding.api.Method;
import io.silksource.silk.coding.api.Project;
import io.silksource.silk.coding.api.Type;
import io.silksource.silk.testdata.FullyQualifiedNameBuilder;
import io.silksource.silk.testdata.IdentifierBuilder;
import io.silksource.silk.testdata.ProjectBuilder;


public class WhenWritingCodeOnTheFileSystem {

  @Rule
  public TemporaryFolder temporaryFolder = new TemporaryFolder();
  private final FullyQualifiedName typeName = FullyQualifiedNameBuilder.someFullyQualifiedName();
  private File projectDir;

  @Before
  public void init() {
    projectDir = new File(temporaryFolder.getRoot(), "project");
  }

  @Test
  public void shouldCreateDirectoriesAndFilesIfTheyDontExist() {
    Project project = new FileBasedProject(projectDir);
    assertTrue("Project dir not created", projectDir.isDirectory());

    File mainSourceDir = new File(projectDir, "src/main/java");
    assertTrue("Source dir not created", mainSourceDir.isDirectory());
    assertTrue("Test dir not created", new File(projectDir, "src/test/java").isDirectory());

    Type type = project.mainSources().addType(typeName);
    assertTrue("Type source not created",
        new File(mainSourceDir, typeName.getInternalName() + ".java").isFile());
    assertTrue("Type not compiled", type.getCompiledPath().toFile().isFile());
  }

  @Test
  public void shouldLoadFromExistingDirectoriesAndFiles() {
    Identifier methodName = IdentifierBuilder.someIdentifier();
    Project project = ProjectBuilder.inDirectory(projectDir)
        .withTestType(typeName)
            .withTestMethod(methodName)
            .end()
        .end()
    .build();

    Optional<Type> type = project.testSources().type(typeName);
    assertTrue("Type not loaded", type.isPresent());

    Optional<Method> method = type.get().method(methodName);
    assertTrue("Method not loaded", method.isPresent());
  }


}
