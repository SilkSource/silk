package io.silksource.silk.code.api;

import java.util.Collection;


public interface Method extends Member {

  Annotation addAnnotation(FullyQualifiedName type);

  Collection<Annotation> getAnnotations();

}
