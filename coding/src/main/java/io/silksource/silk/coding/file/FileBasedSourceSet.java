/*
 * Copyright (c) 2018 SilkSource.
 */
package io.silksource.silk.coding.file;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.apache.commons.io.FilenameUtils;

import io.silksource.silk.coding.api.FullyQualifiedName;
import io.silksource.silk.coding.api.Identifier;
import io.silksource.silk.coding.api.Project;
import io.silksource.silk.coding.api.SourceSet;
import io.silksource.silk.coding.api.Type;
import io.silksource.silk.coding.event.TypeAddedEvent;


public class FileBasedSourceSet implements SourceSet {

  private final Identifier name;
  private final List<Type> types = new ArrayList<>();
  private final Project project;

  public FileBasedSourceSet(Project project, Identifier name) {
    this.project = project;
    this.name = name;
    File dir = getSourcePath().toFile();
    dir.mkdirs();
    addTypesFrom(Optional.empty(), dir);
  }

  private void addTypesFrom(Optional<FullyQualifiedName> parent, File dir) {
    File[] files = dir.listFiles();
    if (files != null) {
      for (File file : files) {
        String identifier = FilenameUtils.removeExtension(file.getName());
        FullyQualifiedName fqn = new FullyQualifiedName(parent, new Identifier(identifier));
        if (file.isDirectory()) {
          addTypesFrom(Optional.of(fqn), file);
        } else if (file.isFile()) {
          doAddType(fqn);
        }
      }
    }
  }

  private Type doAddType(FullyQualifiedName type) {
    Type result = new FileBasedType(this, type);
    types.add(result);
    return result;
  }

  @Override
  public Project getProject() {
    return project;
  }

  @Override
  public Identifier getName() {
    return name;
  }

  @Override
  public Type addType(FullyQualifiedName type) {
    Type result = doAddType(type);
    getProject().fire(new TypeAddedEvent(result));
    return result;
  }

  @Override
  public List<Type> getTypes() {
    return Collections.unmodifiableList(types);
  }

  @Override
  public String toString() {
    return String.format("%s sources", name);
  }

}
