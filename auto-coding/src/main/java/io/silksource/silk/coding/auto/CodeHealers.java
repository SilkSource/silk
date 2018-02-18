/*
 * Copyright (c) 2018 SilkSource.
 */
package io.silksource.silk.coding.auto;

import java.util.Arrays;

import io.silksource.silk.coding.api.Plugin;
import io.silksource.silk.coding.api.Project;
import io.silksource.silk.coding.auto.syntax.CreateMissingClassUnderTest;


public final class CodeHealers {

  private CodeHealers() {
    // Utility class
  }

  private static final Iterable<Plugin> CODE_HEALERS = Arrays.asList(
      new CreateMissingClassUnderTest());

  public static void installIn(Project project) {
    CODE_HEALERS.forEach(plugin -> plugin.installIn(project));
  }

}
