package com.remonsinnema.silk.code.inmemory;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import com.remonsinnema.silk.code.api.Events;
import com.remonsinnema.silk.code.api.Project;
import com.remonsinnema.silk.code.api.SourceSet;
import com.remonsinnema.silk.code.api.SourceSets;


public class InMemoryProject implements Project {

  private final List<SourceSet> sourceSets;
  private final Events events;

  public InMemoryProject() {
    this(new InMemoryEvents());
  }

  public InMemoryProject(Events events) {
    this.sourceSets = Arrays.asList(new InMemorySourceSet(this, SourceSets.MAIN),
        new InMemorySourceSet(this, SourceSets.TEST));
    this.events = events;
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
