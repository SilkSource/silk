/*
 * Copyright (c) 2018 SilkSource.
 */
package io.silksource.silk.coding.api;

import java.util.Locale;


/**
 * Names for standard {@linkplain SourceSet}s.
 */
public enum SourceSetName {

  /**
   * The name of the {@linkplain SourceSet} containing production code.
   */
  MAIN,

  /**
   * The name of the {@linkplain SourceSet} containing test code.
   */
  TEST;

  public Identifier id() {
    return new Identifier(toString());
  }

  @Override
  public String toString() {
    return super.toString().toLowerCase(Locale.ENGLISH);
  }


}
