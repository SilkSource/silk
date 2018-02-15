/*
 * Copyright (c) 2018 SilkSource.
 */
package io.silksource.silk.coding.auto.syntax;

import java.io.IOException;
import java.nio.file.Files;

import io.silksource.silk.coding.api.Project;
import io.silksource.silk.coding.file.FileBasedProject;


public class WhenManipulatingCodeOnTheFileSystem extends WhenManipulatingCode {

  @Override
  protected Project newProject() {
    try {
      return new FileBasedProject(Files.createTempDirectory("project").toFile());
    } catch (IOException e) {
      throw new IllegalStateException("Failed to create project directory", e);
    }
  }

}
