/*
 * Copyright (c) 2018 SilkSource.
 */
package io.silksource.silk.coding.heal;

import io.silksource.silk.coding.api.Events;
import io.silksource.silk.coding.api.Project;


/**
 * Heal code that is broken.
 */
public interface CodeHealer {

  default void installIn(Project project) {
    listenFor(project.getEvents());
  }

  void listenFor(Events events);

}
