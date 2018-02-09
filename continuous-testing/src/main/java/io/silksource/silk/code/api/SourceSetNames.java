package io.silksource.silk.code.api;

import java.util.Locale;


/**
 * Names for standard {@linkplain SourceSet}s.
 */
public enum SourceSetNames {

  /**
   * The name of the {@linkplain SourceSet} containing production code.
   */
  MAIN,

  /**
   * The name of the {@linkplain SourceSet} containing test code.
   */
  TEST;

  @Override
  public String toString() {
    return super.toString().toLowerCase(Locale.ENGLISH);
  }



}
