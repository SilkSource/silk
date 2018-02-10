/*
 * Copyright (c) 2018 SilkSource.
 */
package io.silksource.silk.coding.file;

import io.silksource.silk.coding.api.Annotation;
import io.silksource.silk.coding.api.FullyQualifiedName;


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
