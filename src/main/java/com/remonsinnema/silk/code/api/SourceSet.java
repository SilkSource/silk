package com.remonsinnema.silk.code.api;

import java.util.List;


public interface SourceSet {

  String getName();

  Project getProject();

  Type add(FullyQualifiedName type);

  List<Type> getTypes();

  default Type type(FullyQualifiedName name) {
    return getTypes().stream()
        .filter(t -> t.getName().equals(name))
        .findAny()
        .orElse(null);
  }

}