/*
 * Copyright (c) 2018 SilkSource.
 */
package io.silksource.silk.coding.file;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.eclipse.jdt.core.compiler.batch.BatchCompiler;

import io.silksource.silk.coding.api.CompilationFailedException;
import io.silksource.silk.coding.api.Events;
import io.silksource.silk.coding.api.Identifier;
import io.silksource.silk.coding.api.Project;
import io.silksource.silk.coding.api.Settings;
import io.silksource.silk.coding.api.SourceSet;
import io.silksource.silk.coding.api.SourceSetName;
import io.silksource.silk.coding.api.SourceSynchronizationException;
import io.silksource.silk.coding.api.Type;
import io.silksource.silk.coding.environment.Environment;
import io.silksource.silk.coding.environment.EnvironmentFactory;
import io.silksource.silk.coding.event.TypeChangedEvent;
import io.silksource.silk.coding.event.TypeCompiledEvent;
import io.silksource.silk.coding.impl.DefaultSettings;


public class FileBasedProject implements Project {

  private final List<SourceSet> sourceSets;
  private final Events events;
  private final Path root;
  private final Environment environment;
  private final Settings settings;

  public FileBasedProject(File dir) {
    try {
      File file = dir.getCanonicalFile();
      file.mkdirs();
      this.root = file.toPath();
    } catch (IOException e) {
      throw new SourceSynchronizationException("Failed to canonicalize project dir: " + dir, e);
    }
    this.environment = EnvironmentFactory.newInstance();
    this.events = new Events();
    events.listenFor(TypeChangedEvent.class, this::typeChanged);
    this.sourceSets = new ArrayList<>();
    addSourceSet(SourceSetName.MAIN.id());
    addSourceSet(SourceSetName.TEST.id());
    this.settings = new DefaultSettings();
    settings.set(Settings.COMPILED_TESTS_PATH, testSources().getCompiledPath());
  }

  private void typeChanged(TypeChangedEvent event) {
    compile(event.getType());
  }

  private void compile(Type type) {
    compile(type.getSourcePath(), type.getSourceSet().getCompiledPath());
    fire(new TypeCompiledEvent(type));
  }

  private void compile(Path source, Path destination) {
    StringWriter output = new StringWriter();
    try (PrintWriter writer = new PrintWriter(output)) {
      String commandLine = getCommandLine(source, destination);
      if (!BatchCompiler.compile(commandLine, writer, writer, null)) {
        throw new CompilationFailedException(commandLine, output.toString());
      }
    }
  }

  private String getCommandLine(Path source, Path destination) {
    StringBuilder result = new StringBuilder();
    result.append("-source 1.8 ").append(source);
    result.append(" -d ").append(destination);
    return result.toString();
  }

  @Override
  public Path getScrathPath() {
    return root.resolve(environment.getScratchDir());
  }

  @Override
  public Path getSourcePath() {
    return root.resolve(environment.getSourceDir());
  }

  @Override
  public Path getCompiledPath() {
    return root.resolve(environment.getCompiledDir());
  }

  @Override
  public Events getEvents() {
    return events;
  }

  @Override
  public Settings getSettings() {
    return settings;
  }

  @Override
  public final SourceSet addSourceSet(Identifier name) {
    SourceSet result = new FileBasedSourceSet(this, name);
    sourceSets.add(result);
    return result;
  }

  @Override
  public Collection<SourceSet> getSourceSets() {
    return Collections.unmodifiableList(sourceSets);
  }

}
