/*
 * Copyright (c) 2018 SilkSource.
 */
package io.silksource.silk.coding.environment;


public class GradleEnvironment implements Environment {

  @Override
  public String getSourceDir() {
    return "src";
  }

  @Override
  public String getCompiledDir() {
    return "build/classes/java";
  }

}
