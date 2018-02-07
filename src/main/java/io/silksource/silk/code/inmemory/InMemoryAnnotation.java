package io.silksource.silk.code.inmemory;

import io.silksource.silk.code.api.Annotation;
import io.silksource.silk.code.api.FullyQualifiedName;


public class InMemoryAnnotation implements Annotation {

  private final FullyQualifiedName type;

  public InMemoryAnnotation(FullyQualifiedName type) {
    this.type = type;
  }

  @Override
  public FullyQualifiedName getType() {
    return type;
  }

}
