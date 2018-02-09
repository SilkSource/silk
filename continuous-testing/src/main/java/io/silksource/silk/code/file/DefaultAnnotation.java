package io.silksource.silk.code.file;

import io.silksource.silk.code.api.Annotation;
import io.silksource.silk.code.api.FullyQualifiedName;


public class DefaultAnnotation implements Annotation {

  private final FullyQualifiedName type;

  public DefaultAnnotation(FullyQualifiedName type) {
    this.type = type;
  }

  @Override
  public FullyQualifiedName getType() {
    return type;
  }

}
