package com.remonsinnema.silk.code.inmemory;

import com.remonsinnema.silk.code.api.Events;
import com.remonsinnema.silk.code.api.Project;
import com.remonsinnema.silk.code.heal.WhenManipulatingCode;
import com.remonsinnema.silk.code.inmemory.InMemoryProject;


public class WhenManipulatingCodeInMemory extends WhenManipulatingCode {

  @Override
  protected Project newProject(Events events) {
    return new InMemoryProject(events);
  }

}
