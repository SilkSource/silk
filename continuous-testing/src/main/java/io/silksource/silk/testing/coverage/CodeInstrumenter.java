/*
 * Copyright (c) 2018 SilkSource.
 */
package io.silksource.silk.testing.coverage;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;

import io.silksource.silk.coding.api.Events;
import io.silksource.silk.coding.api.Plugin;
import io.silksource.silk.coding.api.Project;
import io.silksource.silk.coding.api.Settings;
import io.silksource.silk.coding.api.SourceSet;
import io.silksource.silk.coding.api.SourceSetName;
import io.silksource.silk.coding.api.Type;
import io.silksource.silk.coding.event.TypeCompiledEvent;


/**
 * Base plugin for instrumenting code to measure coverage.
 */
public abstract class CodeInstrumenter implements Plugin {

  private static final String BASE_DIR = "instrumented";

  /**
   * Instrument some code.
   * @param name the name of the code
   * @param input the location of the original code
   * @param output the location of the instrumented code
   * @throws IOException When an I/O error occurs
   */
  protected abstract void instrument(String name, InputStream input, OutputStream output) throws IOException;

  @Override
  public void init(Project project) {
    Path projectOutputPath = getOutputPath(project);
    project.getSourceSets().forEach(sourceSet ->
        instrument(sourceSet, projectOutputPath));
  }

  private Path getOutputPath(Project project) {
    return project.getScrathPath().resolve(BASE_DIR);
  }

  private void instrument(SourceSet sourceSet, Path projectOutputPath) {
    Path sourceSetOutputPath = getOutputPath(projectOutputPath, sourceSet);
    if (sourceSet.getName().equals(SourceSetName.TEST.id())) {
      sourceSet.getProject().getSettings().set(Settings.COMPILED_TESTS_PATH, sourceSetOutputPath);
    }
    sourceSet.getTypes().stream()
        .filter(type -> type.getCompiledPath().toFile().isFile())
        .forEach(type ->
            instrument(type, sourceSetOutputPath));
  }

  private Path getOutputPath(Path projectOutputPath, SourceSet sourceSet) {
    return projectOutputPath.resolve(sourceSet.getName().toString());
  }

  private void instrument(Type type, Path sourceSetOutputPath) {
    Path typeOutputPath = getOutputPath(sourceSetOutputPath, type);
    typeOutputPath.toFile().getParentFile().mkdirs();
    try (InputStream input = Files.newInputStream(type.getCompiledPath(), StandardOpenOption.READ)) {
      try (OutputStream output = Files.newOutputStream(typeOutputPath, StandardOpenOption.CREATE)) {
        instrument(type.getName().toString(), input, output);
      }
    } catch (IOException e) {
      throw new FailedToInstrumentTypeException(type, e);
    }
  }

  private Path getOutputPath(Path sourceSetOutputPath, Type type) {
    return sourceSetOutputPath.resolve(type.getName().getInternalName() + ".class");
  }

  @Override
  public void listenFor(Events events) {
    events.listenFor(TypeCompiledEvent.class, event ->
        instrument(event.getType()));
  }

  void instrument(Type type) {
    instrument(type, getOutputPath(getOutputPath(type.getProject()), type.getSourceSet()));
  }

}
