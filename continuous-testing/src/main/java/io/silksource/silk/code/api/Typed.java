/*
 * Copyright (c) 2018 SilkSource.
 */
package io.silksource.silk.code.api;


/**
 * Something that has a type.
 */
public interface Typed {

  /**
   * Returns the name of the type.
   * @return the name of the type
   */
  FullyQualifiedName getType();

}
