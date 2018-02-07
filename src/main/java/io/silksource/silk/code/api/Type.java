package io.silksource.silk.code.api;

import java.util.List;


public interface Type {

  FullyQualifiedName getName();

  SourceSet getSourceSet();

  default Project getProject() {
    return getSourceSet().getProject();
  }

  Member addField(FullyQualifiedName type, String name);

  List<Field> getFields();

}
