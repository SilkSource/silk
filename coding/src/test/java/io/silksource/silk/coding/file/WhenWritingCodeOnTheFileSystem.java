/*
 * Copyright (c) 2018 SilkSource.
 */
package io.silksource.silk.coding.file;

import static org.junit.Assert.assertTrue;

import java.io.File;
import java.util.Optional;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import io.silksource.silk.coding.api.FullyQualifiedName;
import io.silksource.silk.coding.api.Project;
import io.silksource.silk.coding.api.SourceSetNames;
import io.silksource.silk.coding.api.Type;
import io.silksource.silk.testdata.FullyQualifiedNameBuilder;


public class WhenWritingCodeOnTheFileSystem {

  @Rule
  public TemporaryFolder temporaryFolder = new TemporaryFolder();
  private final FullyQualifiedName typeName = FullyQualifiedNameBuilder.someFullyQualifiedName();

  @Test
  public void shouldCreateDirectoriesAndFilesIfTheyDontExist() {
    File projectDir = new File(temporaryFolder.getRoot(), "project");

    Project project = new FileBasedProject(projectDir);
    assertTrue("Project dir not created", projectDir.isDirectory());
    File mainSourceDir = new File(projectDir, "src/main/java");
    assertTrue("Source dir not created", mainSourceDir.isDirectory());
    assertTrue("Test dir not created", new File(projectDir, "src/test/java").isDirectory());

    Type type = project.sourceSet(SourceSetNames.MAIN.toString()).get().addType(typeName);
    assertTrue("Type source not created",
        new File(mainSourceDir, typeName.getInternalName() + ".java").isFile());
    assertTrue("Type not compiled", type.getCompiledPath().toFile().isFile());
  }

  @Test
  public void shouldLoadFromExistingDirectoriesAndFiles() {
    File projectDir = new File(".").getAbsoluteFile();

    Project project = new FileBasedProject(projectDir);

    Optional<Type> thisType = project.sourceSet(SourceSetNames.TEST.toString())
        .get()
        .type(new FullyQualifiedName(getClass().getName()));
    assertTrue("Type not loaded", thisType.isPresent());
  }


}
