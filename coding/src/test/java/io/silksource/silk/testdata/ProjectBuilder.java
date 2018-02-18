/*
 * Copyright (c) 2018 SilkSource.
 */
package io.silksource.silk.testdata;

import java.io.File;

import io.silksource.silk.coding.api.FullyQualifiedName;
import io.silksource.silk.coding.api.Project;
import io.silksource.silk.coding.file.FileBasedProject;


public class ProjectBuilder {

  public static ProjectBuilder inDirectory(File directory) {
    return new ProjectBuilder(directory);
  }

  private final FileBasedProject project;

  public ProjectBuilder(File directory) {
    project = new FileBasedProject(directory);
  }

  public Project build() {
    return project;
  }

  public TypeBuilder withTestType(FullyQualifiedName fqn) {
    return withTestType(fqn.toString());
  }

  public TypeBuilder withTestType(String fqn) {
    return new TypeBuilder(this, project.testSources(), fqn);
  }

}
