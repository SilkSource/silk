/*
 * Copyright (c) 2018 SilkSource.
 */
package io.silksource.silk.coding.file;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.objectweb.asm.ClassReader;

import io.silksource.silk.coding.api.Field;
import io.silksource.silk.coding.api.FullyQualifiedName;
import io.silksource.silk.coding.api.Method;
import io.silksource.silk.coding.api.SourceSet;
import io.silksource.silk.coding.api.SourceSynchronizationException;
import io.silksource.silk.coding.api.Type;
import io.silksource.silk.coding.event.FieldAddedEvent;
import io.silksource.silk.coding.event.MethodAddedEvent;
import io.silksource.silk.coding.event.TypeChangedEvent;
import io.silksource.silk.coding.event.TypeCompiledEvent;
import io.silksource.silk.coding.impl.DefaultField;
import io.silksource.silk.coding.impl.DefaultMethod;


public class FileBasedType implements Type {

  private static final String NL = System.lineSeparator();
  private final SourceSet sourceSet;
  private final FullyQualifiedName name;
  private final List<Field> fields = new ArrayList<>();
  private final List<Method> methods = new ArrayList<>();
  private boolean loading;

  public FileBasedType(SourceSet sourceSet, FullyQualifiedName name) {
    this.sourceSet = sourceSet;
    this.name = name;
    getEvents().listenFor(TypeCompiledEvent.class, this::typeCompiled);
    load();
  }

  private void typeCompiled(TypeCompiledEvent event) {
    if (equals(event.getType())) {
      loadCompiled();
    }
  }

  private void loadCompiled() {
    loading = true;
    try (InputStream input = Files.newInputStream(getCompiledPath(), StandardOpenOption.READ)) {
      ClassReader reader = new ClassReader(input);
      reader.accept(new TypeLoader(this), 0);
    } catch (IOException e) {
      throw new SourceSynchronizationException("Failed to load class bytes for " + this, e);
    } finally {
      loading = false;
    }
  }

  private void load() {
    if (getCompiledPath().toFile().exists()) {
      loadCompiled();
    } else if (getSourcePath().toFile().exists()) {
      // Will make sure this type gets compiled, so we'll end up in the branch above
      changed();
    } else {
      // Will also make sure this type gets compiled and then loaded via the first branch
      setInitialText();
    }
  }

  private void changed() {
    changed(new TypeChangedEvent(this));
  }

  private void changed(TypeChangedEvent event) {
    if (!loading) {
      getEvents().fire(event);
    }
  }

  private void setInitialText() {
    Optional.ofNullable(getSourcePath().getParent())
        .map(Path::toFile)
        .ifPresent(File::mkdirs);
    setText(getInitialText());
  }

  private String getInitialText() {
    StringBuilder result = new StringBuilder();
    name.getNamespace().ifPresent(parent ->
        result.append("package ").append(parent).append(';').append(NL).append(NL));
    result.append("public class ").append(name.getSimpleName()).append(" {").append(NL)
        .append('}').append(NL);
    return result.toString();
  }

  @Override
  public final void setText(String text) {
    try (Writer writer = Files.newBufferedWriter(getSourcePath(), StandardOpenOption.CREATE)) {
      writer.write(text);
    } catch (IOException e) {
      throw new SourceSynchronizationException("Could not write text", e);
    }
    changed();
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
  public Field addField(String fieldName, FullyQualifiedName type) {
    Field result = new DefaultField(this, fieldName, type);
    fields.add(result);
    changed(new FieldAddedEvent(result));
    return result;
  }

  @Override
  public List<Field> getFields() {
    return Collections.unmodifiableList(fields);
  }

  @Override
  public Method addMethod(String methodName) {
    Method result = new DefaultMethod(this, methodName);
    methods.add(result);
    changed(new MethodAddedEvent(result));
    return result;
  }

  @Override
  public List<Method> getMethods() {
    return Collections.unmodifiableList(methods);
  }

  @Override
  public int hashCode() {
    return name.hashCode();
  }

  @Override
  public boolean equals(Object other) {
    if (other instanceof Type) {
      return name.equals(((Type)other).getName());
    }
    return false;
  }

  @Override
  public String toString() {
    return name.toString();
  }

}
