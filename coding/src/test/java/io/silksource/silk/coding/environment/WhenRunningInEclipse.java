/*
 * Copyright (c) 2018 SilkSource.
 */
package io.silksource.silk.coding.environment;


public class WhenRunningInEclipse extends AbstractEnvironmentTestCase {

  @Override
  protected Environment newEnvironment() {
    return new EclipseEnvironment();
  }

  @Override
  protected String expectedSourceDir() {
    return "src";
  }

  @Override
  protected String expectedCompiledDir() {
    return "classes";
  }

}
