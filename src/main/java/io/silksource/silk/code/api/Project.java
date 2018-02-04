package io.silksource.silk.code.api;

import java.util.Collection;


public interface Project {

  Collection<SourceSet> getSourceSets();

  default SourceSet sourceSet(String name) {
    return getSourceSets().stream()
        .filter(s -> s.getName().equals(name))
        .findAny()
        .orElse(null);
  }

  Events getEvents();

  default void fire(Object event) {
    getEvents().fire(event);
  }

}
