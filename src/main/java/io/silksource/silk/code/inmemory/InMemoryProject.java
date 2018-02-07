package io.silksource.silk.code.inmemory;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import io.silksource.silk.code.api.Events;
import io.silksource.silk.code.api.Project;
import io.silksource.silk.code.api.SourceSet;
import io.silksource.silk.code.api.SourceSets;


public class InMemoryProject implements Project {

  private final List<SourceSet> sourceSets;
  private final Events events;

  public InMemoryProject() {
    this.sourceSets = new ArrayList<>();
    addSourceSet(SourceSets.MAIN);
    addSourceSet(SourceSets.TEST);
    this.events = new InMemoryEvents();
  }

  @Override
  public SourceSet addSourceSet(String name) {
    SourceSet result = new InMemorySourceSet(this, name);
    sourceSets.add(result);
    return result;
  }

  @Override
  public Collection<SourceSet> getSourceSets() {
    return Collections.unmodifiableList(sourceSets);
  }

  @Override
  public Events getEvents() {
    return events;
  }

}
