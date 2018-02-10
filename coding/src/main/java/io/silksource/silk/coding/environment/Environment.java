/*
 * Copyright (c) 2018 SilkSource.
 */
package io.silksource.silk.coding.environment;


/**
 * The environment in which coding takes place.
 */
public interface Environment {

  /**
   * Returns the path to the sources, relative to the project directory.
   * @return the path to the sources, relative to the project directory
   */
  String getSourceDir();

  /**
   * Returns the path to the compiled sources, relative to the project directory.
   * @return the path to the compiled sources, relative to the project directory
   */
  String getCompiledDir();

}
