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

import io.silksource.silk.coding.api.Plugin;
import io.silksource.silk.coding.api.Project;
import io.silksource.silk.coding.api.SourceSet;
import io.silksource.silk.coding.api.Type;


/**
 * Base plugin for instrumenting code to measure coverage.
 */
public abstract class CodeInstrumenter implements Plugin {

  private static final String BASE_DIR = "instrumented";

  @Override
  public void init(Project project) {
    Path projectOutputPath = project.getScrathPath().resolve(BASE_DIR);
    project.getSourceSets().forEach(sourceSet ->
        instrument(sourceSet, projectOutputPath));
  }

  private void instrument(SourceSet sourceSet, Path projectOutputPath) {
    Path sourceSetOutputPath = projectOutputPath.resolve(sourceSet.getName().toString());
    sourceSet.getTypes().stream()
        .filter(type -> type.getCompiledPath().toFile().isFile())
        .forEach(type ->
            instrument(type, sourceSetOutputPath));
  }

  private void instrument(Type type, Path sourceSetOutputPath) {
    Path typeOutputPath = sourceSetOutputPath.resolve(type.getName().getInternalName() + ".class");
    typeOutputPath.toFile().getParentFile().mkdirs();
    try (InputStream input = Files.newInputStream(type.getCompiledPath(), StandardOpenOption.READ)) {
      try (OutputStream output = Files.newOutputStream(typeOutputPath, StandardOpenOption.CREATE)) {
        instrument(type.getName().toString(), input, output);
      }
    } catch (IOException e) {
      throw new FailedToInstrumentTypeException(type, e);
    }
  }

  /**
   * Instrument some code.
   * @param name the name of the code
   * @param input the location of the original code
   * @param output the location of the instrumented code
   * @throws IOException When an I/O error occurs
   */
  protected abstract void instrument(String name, InputStream input, OutputStream output) throws IOException;

}
