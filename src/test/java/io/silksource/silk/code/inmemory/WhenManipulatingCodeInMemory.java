package io.silksource.silk.code.inmemory;

import com.google.common.io.Files;

import io.silksource.silk.code.api.Project;
import io.silksource.silk.code.file.FileBasedProject;
import io.silksource.silk.code.heal.WhenManipulatingCode;


public class WhenManipulatingCodeInMemory extends WhenManipulatingCode {

  @Override
  protected Project newProject() {
    return new FileBasedProject(Files.createTempDir());
  }

}
