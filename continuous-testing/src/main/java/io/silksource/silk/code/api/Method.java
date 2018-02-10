/*
 * Copyright (c) 2018 SilkSource.
 */
package io.silksource.silk.code.api;

import java.util.Collection;


/**
 * Behavior in a {@linkplain Type}.
 */
public interface Method extends Member {

  /**
   * Adds an annotation of the given type to the method.
   * @param type the type of annotation to add
   * @return the added annotation
   */
  Annotation addAnnotation(FullyQualifiedName type);

  /**
   * Returns all the annotations of the method.
   * @return all the annotations of the method
   */
  Collection<Annotation> getAnnotations();

}
