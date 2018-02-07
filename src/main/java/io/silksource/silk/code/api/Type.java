package io.silksource.silk.code.api;

import java.util.List;


public interface Type {

  FullyQualifiedName getName();

  SourceSet getSourceSet();

  default Project getProject() {
    return getSourceSet().getProject();
  }

  Field addField(String name, FullyQualifiedName type);

  List<Field> getFields();

  Method addMethod(String name);

}
