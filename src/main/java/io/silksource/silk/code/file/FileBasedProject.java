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
import io.silksource.silk.code.api.Type;
import io.silksource.silk.code.event.TypeChangedEvent;


public class FileBasedProject implements Project {

  private final List<SourceSet> sourceSets;
  private final Events events;
  private final Path root;

  public FileBasedProject(File dir) {
    try {
      this.root = dir.getCanonicalFile().toPath();
    } catch (IOException e) {
      throw new SourceSynchronizationException("Failed to canonicalize project dir: " + dir, e);
    }
    this.events = new Events();
    events.listenFor(TypeChangedEvent.class, this::typeChanged);
    this.sourceSets = new ArrayList<>();
    addSourceSet(SourceSetNames.MAIN);
    addSourceSet(SourceSetNames.TEST);
  }

  private void typeChanged(TypeChangedEvent event) {
    compile(event.getType());
  }

  private void compile(Type type) {
    // TODO: Compile the type to a .class file
    // Using Eclipse incremental Java compiler?
    System.out.println("Compiling " + type.getName());
  }

  @Override
  public Path getSourcePath() {
    return root.resolve("src");
  }

  @Override
  public Path getCompiledPath() {
    return root.resolve("classes");
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
