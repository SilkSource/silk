/*
 * Copyright (c) 2018 SilkSource.
 */
package io.silksource.silk.coding.environment;


public class WhenRunningInGradle extends AbstractEnvironmentTestCase {

  @Override
  protected Environment newEnvironment() {
    return new GradleEnvironment();
  }

  @Override
  protected String expectedSourceDir() {
    return "src";
  }

  @Override
  protected String expectedCompiledDir() {
    return "build/classes/java";
  }

}
