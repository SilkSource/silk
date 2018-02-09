package io.silksource.silk.code.file;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

import io.silksource.silk.code.api.Annotation;
import io.silksource.silk.code.api.FullyQualifiedName;
import io.silksource.silk.code.api.Method;
import io.silksource.silk.code.api.Type;


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
