package io.silksource.silk.code.inmemory;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

import io.silksource.silk.code.api.Annotation;
import io.silksource.silk.code.api.FullyQualifiedName;
import io.silksource.silk.code.api.Method;
import io.silksource.silk.code.api.Type;


public class InMemoryMethod extends InMemoryMember implements Method {

  private final Collection<Annotation> annotations = new ArrayList<>();

  public InMemoryMethod(Type ownerType, String name) {
    super(ownerType, name);
  }

  @Override
  public Annotation addAnnotation(FullyQualifiedName type) {
    Annotation result = new InMemoryAnnotation(type);
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
