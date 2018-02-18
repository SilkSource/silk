/*
 * Copyright (c) 2018 SilkSource.
 */
package io.silksource.silk.coding.auto.syntax;

import org.junit.Rule;
import org.junit.rules.TemporaryFolder;

import io.silksource.silk.coding.api.Project;
import io.silksource.silk.coding.file.FileBasedProject;


public class WhenManipulatingCodeOnTheFileSystem extends WhenManipulatingCode {

  @Rule
  public TemporaryFolder temporaryFolder = new TemporaryFolder();

  @Override
  protected Project newProject() {
    return new FileBasedProject(temporaryFolder.getRoot());
  }

}
