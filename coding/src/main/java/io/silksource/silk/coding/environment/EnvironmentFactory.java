/*
 * Copyright (c) 2018 SilkSource.
 */
package io.silksource.silk.coding.environment;

import java.util.Locale;


/**
 * Factory for creating an {@linkplain Environment}.
 */
public final class EnvironmentFactory {

  private EnvironmentFactory() {
    // Utility class
  }

  public static Environment newInstance() {
    String startUpCommand = System.getProperty("sun.java.command", "").toLowerCase(Locale.ENGLISH);
    if (startUpCommand.contains("gradle")) {
      return new GradleEnvironment();
    }
    if (startUpCommand.contains("eclipse")) {
      return new EclipseEnvironment();
    }
    throw new IllegalStateException("Unknown environment started by " + startUpCommand);
  }

}
