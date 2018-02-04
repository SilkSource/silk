package com.remonsinnema.silk.code.api;

import java.util.List;


public interface Type {

  FullyQualifiedName getName();

  SourceSet getSourceSet();

  default Project getProject() {
    return getSourceSet().getProject();
  }

  Field addField(FullyQualifiedName type, String name);

  List<Field> getFields();

}
