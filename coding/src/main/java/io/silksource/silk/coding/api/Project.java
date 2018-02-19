/*
 * Copyright (c) 2018 SilkSource.
 */
package io.silksource.silk.coding.api;

import java.nio.file.Path;
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
  SourceSet addSourceSet(Identifier name);

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
  default Optional<SourceSet> sourceSet(Identifier name) {
    return getSourceSets().stream()
        .filter(s -> s.getName().equals(name))
        .findAny();
  }

  default SourceSet mainSources() {
    return namedSourceSet(SourceSetName.MAIN);
  }

  default SourceSet namedSourceSet(SourceSetName sourceSetName) {
    return sourceSet(sourceSetName.id())
        .orElseThrow(() -> new IllegalStateException("Missing " + sourceSetName + " sources"));
  }

  default SourceSet testSources() {
    return namedSourceSet(SourceSetName.TEST);
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

  /**
   * Returns the location on the file system where temporary files are stored.
   * @return the location on the file system where temporary files are stored
   */
  Path getScrathPath();

  /**
   * Returns the project's settings.
   * @return the project's settings
   */
  Settings getSettings();

}
