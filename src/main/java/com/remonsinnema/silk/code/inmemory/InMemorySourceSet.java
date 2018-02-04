package com.remonsinnema.silk.code.inmemory;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.remonsinnema.silk.code.api.FullyQualifiedName;
import com.remonsinnema.silk.code.api.Project;
import com.remonsinnema.silk.code.api.SourceSet;
import com.remonsinnema.silk.code.api.Type;
import com.remonsinnema.silk.code.event.TypeAddedEvent;


public class InMemorySourceSet implements SourceSet {

  private final String name;
  private final List<Type> types = new ArrayList<>();
  private final Project project;

  public InMemorySourceSet(Project project, String name) {
    this.project = project;
    this.name = name;
  }

  @Override
  public Project getProject() {
    return project;
  }

  @Override
  public String getName() {
    return name;
  }

  @Override
  public Type add(FullyQualifiedName type) {
    Type result = new InMemoryType(this, type);
    types.add(result);
    getProject().fire(new TypeAddedEvent(result));
    return result;
  }

  @Override
  public List<Type> getTypes() {
    return Collections.unmodifiableList(types);
  }

}
