package com.remonsinnema.silk.code.heal;

import com.remonsinnema.silk.code.api.Events;
import com.remonsinnema.silk.code.api.Project;


/**
 * Heal code that is broken.
 */
public interface CodeHealer {

  default void installIn(Project project) {
    listenFor(project.getEvents());
  }

  void listenFor(Events events);

}
