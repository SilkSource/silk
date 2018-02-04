package io.silksource.silk.code.heal;

import io.silksource.silk.code.api.Events;
import io.silksource.silk.code.api.Project;


/**
 * Heal code that is broken.
 */
public interface CodeHealer {

  default void installIn(Project project) {
    listenFor(project.getEvents());
  }

  void listenFor(Events events);

}
