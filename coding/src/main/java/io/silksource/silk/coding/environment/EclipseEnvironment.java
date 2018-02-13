/*
 * Copyright (c) 2018 SilkSource.
 */
package io.silksource.silk.coding.environment;


public class EclipseEnvironment implements Environment {

  @Override
  public String getSourceDir() {
    return "src";
  }

  @Override
  public String getCompiledDir() {
    return "classes";
  }

  @Override
  public String getScratchDir() {
    return ".settings";
  }

}
