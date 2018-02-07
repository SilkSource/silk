package io.silksource.silk.code.inmemory;

import io.silksource.silk.code.api.Project;
import io.silksource.silk.code.heal.WhenManipulatingCode;


public class WhenManipulatingCodeInMemory extends WhenManipulatingCode {

  @Override
  protected Project newProject() {
    return new InMemoryProject();
  }

}
