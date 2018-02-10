/*
 * Copyright (c) 2018 SilkSource.
 */
package io.silksource.silk.coding.file;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

import io.silksource.silk.coding.api.Annotation;
import io.silksource.silk.coding.api.FullyQualifiedName;
import io.silksource.silk.coding.api.Method;
import io.silksource.silk.coding.api.Type;


public class DefaultMethod extends DefaultMember implements Method {

  private final Collection<Annotation> annotations = new ArrayList<>();

  public DefaultMethod(Type ownerType, String name) {
    super(ownerType, name);
  }

  @Override
  public Annotation addAnnotation(FullyQualifiedName type) {
    Annotation result = new DefaultAnnotation(type);
    annotations.add(result);
    return result;
  }

  @Override
  public Collection<Annotation> getAnnotations() {
    return Collections.unmodifiableCollection(annotations);
  }

  @Override
  public String toString() {
    return String.format("%s()", super.toString());
  }

}
