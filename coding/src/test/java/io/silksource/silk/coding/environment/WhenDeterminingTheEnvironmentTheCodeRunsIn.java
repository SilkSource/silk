/*
 * Copyright (c) 2018 SilkSource.
 */
package io.silksource.silk.coding.environment;

import static org.junit.Assert.assertEquals;

import org.junit.Test;


public class WhenDeterminingTheEnvironmentTheCodeRunsIn {

  @Test
  public void shouldDecideOnGradleWhenRunningInGradle() {
    assertEnvironment("gradle", GradleEnvironment.class);
  }

  private void assertEnvironment(String command, Class<? extends Environment> expectedType) {
    System.setProperty("sun.java.command", command);

    Environment environment = EnvironmentFactory.newInstance();

    assertEquals("Environment", expectedType, environment.getClass());
  }

  @Test
  public void shouldDecideOnEclipseWhenRunningInEclipse() {
    assertEnvironment("eclipse", EclipseEnvironment.class);
  }

  @Test(expected = IllegalStateException.class)
  public void shouldThrowErrorWhenRunningInUnknowEnvironment() {
    assertEnvironment("unknown", null);
  }


}
