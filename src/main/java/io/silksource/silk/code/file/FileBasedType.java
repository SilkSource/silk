package io.silksource.silk.code.file;

import java.io.File;
import java.io.IOException;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import io.silksource.silk.code.api.Field;
import io.silksource.silk.code.api.FullyQualifiedName;
import io.silksource.silk.code.api.Method;
import io.silksource.silk.code.api.SourceSet;
import io.silksource.silk.code.api.Type;
import io.silksource.silk.code.event.FieldAddedEvent;
import io.silksource.silk.code.event.MethodAddedEvent;


public class FileBasedType implements Type {

  private static final String NL = System.lineSeparator();
  private final SourceSet sourceSet;
  private final FullyQualifiedName name;
  private final List<Field> fields = new ArrayList<>();
  private final List<Method> methods = new ArrayList<>();

  public FileBasedType(SourceSet sourceSet, FullyQualifiedName name) {
    this.sourceSet = sourceSet;
    this.name = name;
    File file = getSourcePath().toFile();
    if (!file.isFile()) {
      file.getParentFile().mkdirs();
      setText(getInitialText());
    }
  }

  private String getInitialText() {
    StringBuilder result = new StringBuilder();
    name.getParent().ifPresent(parent ->
        result.append("package ").append(parent).append(';').append(NL).append(NL));
    result.append("public class ").append(name.getSimpleName()).append(" {").append(NL)
        .append("}").append(NL);
    return result.toString();
  }

  private void setText(String text) {
    try (Writer writer = Files.newBufferedWriter(getSourcePath(), StandardOpenOption.CREATE)) {
      writer.write(text);
    } catch (IOException e) {
      throw new SourceSynchronizationException("Could not write text", e);
    }
  }

  @Override
  public FullyQualifiedName getName() {
    return name;
  }

  @Override
  public SourceSet getSourceSet() {
    return sourceSet;
  }

  @Override
  public Field addField(String name, FullyQualifiedName type) {
    Field result = new DefaultField(this, name, type);
    fields.add(result);
    getProject().fire(new FieldAddedEvent(result));
    return result;
  }

  @Override
  public List<Field> getFields() {
    return Collections.unmodifiableList(fields);
  }

  @Override
  public Method addMethod(String name) {
    Method result = new DefaultMethod(this, name);
    methods.add(result);
    getProject().fire(new MethodAddedEvent(result));
    return result;
  }

  @Override
  public String toString() {
    return name.toString();
  }

  @Override
  public List<Method> getMethods() {
    return Collections.unmodifiableList(methods);
  }

}
