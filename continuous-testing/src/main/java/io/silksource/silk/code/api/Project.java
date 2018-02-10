/*
 * Copyright (c) 2018 SilkSource.
 */
package io.silksource.silk.code.api;

import java.util.Collection;
import java.util.Optional;


/**
 * Collection of sources that belong together. Sources that serve the same purpose are grouped in
 * {@linkplain SourceSet}s.
 */
public interface Project extends FileBased {

  /**
   * Add a source set to the project.
   * @param name The name of the source set
   * @return the added source set
   */
  SourceSet addSourceSet(String name);

  /**
   * Returns the source sets in the project.
   * @return the source sets in the project
   */
  Collection<SourceSet> getSourceSets();

  /**
   * Returns the source set with the given name, if any.
   * @param name the name of the source set to return
   * @return an optional source set
   */
  default Optional<SourceSet> sourceSet(String name) {
    return getSourceSets().stream()
        .filter(s -> s.getName().equals(name))
        .findAny();
  }

  /**
   * Returns the events for the project.
   * @return the events for the project
   */
  Events getEvents();

  /**
   * Notify others that something interesting happened.
   * @param event the interesting thing that happened
   */
  default void fire(Object event) {
    getEvents().fire(event);
  }

}
