package io.silksource.silk.code.file;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import io.silksource.silk.code.api.Events;
import io.silksource.silk.code.api.Project;
import io.silksource.silk.code.api.SourceSet;
import io.silksource.silk.code.api.SourceSetNames;


public class FileBasedProject implements Project {

  private final List<SourceSet> sourceSets;
  private final Events events;
  private final Path path;

  public FileBasedProject(File dir) {
    try {
      this.path = dir.getCanonicalFile().toPath();
    } catch (IOException e) {
      throw new SourceSynchronizationException("Failed to canonicalize project dir: " + dir, e);
    }
    this.sourceSets = new ArrayList<>();
    addSourceSet(SourceSetNames.MAIN);
    addSourceSet(SourceSetNames.TEST);
    this.events = new Events();
  }

  @Override
  public Path getSourcePath() {
    return path;
  }

  @Override
  public Path getCompiledPath() {
    return path;
  }

  @Override
  public Events getEvents() {
    return events;
  }

  @Override
  public SourceSet addSourceSet(String name) {
    SourceSet result = new FileBasedSourceSet(this, name);
    sourceSets.add(result);
    return result;
  }

  @Override
  public Collection<SourceSet> getSourceSets() {
    return Collections.unmodifiableList(sourceSets);
  }

}
