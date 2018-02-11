/*
 * Copyright (c) 2018 SilkSource.
 */
package io.silksource.silk.coding.environment;

import static org.junit.Assert.assertEquals;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;


public class WhenDeterminingTheEnvironmentTheCodeRunsIn {

  private static final String STARTUP_COMMAND_PROP = "sun.java.command";

  private String saveStartupCommand;

  @Before
  public void init() {
    saveStartupCommand = System.getProperty(STARTUP_COMMAND_PROP);
  }

  @After
  public void done() {
    System.setProperty(STARTUP_COMMAND_PROP, saveStartupCommand);
  }

  @Test
  public void shouldDecideOnGradleWhenRunningInGradle() {
    assertEnvironment("gradle", GradleEnvironment.class);
  }

  private void assertEnvironment(String command, Class<? extends Environment> expectedType) {
    System.setProperty(STARTUP_COMMAND_PROP, command);

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
