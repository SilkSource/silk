package io.silksource.silk.code.inmemory;

import io.silksource.silk.code.api.Events;
import io.silksource.silk.code.api.Project;
import io.silksource.silk.code.heal.WhenManipulatingCode;
import io.silksource.silk.code.inmemory.InMemoryProject;


public class WhenManipulatingCodeInMemory extends WhenManipulatingCode {

  @Override
  protected Project newProject(Events events) {
    return new InMemoryProject(events);
  }

}
