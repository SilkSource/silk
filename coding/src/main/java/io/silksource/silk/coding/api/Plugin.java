/*
 * Copyright (c) 2018 SilkSource.
 */
package io.silksource.silk.coding.api;

/**
 * Extend the functionality of a project.
 */
public interface Plugin {

  default void installIn(Project project) {
    listenFor(project.getEvents());
    init(project);
  }

  default void init(Project project) {
    // Optional functionality
  }

  void listenFor(Events events);

}
