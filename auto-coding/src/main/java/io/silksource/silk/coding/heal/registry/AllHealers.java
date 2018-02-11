/*
 * Copyright (c) 2018 SilkSource.
 */
package io.silksource.silk.coding.heal.registry;

import java.util.Arrays;

import io.silksource.silk.coding.api.Project;
import io.silksource.silk.coding.heal.CodeHealer;
import io.silksource.silk.coding.heal.syntax.CreateMissingClassUnderTest;


public final class AllHealers {

  private AllHealers() {
    // Utility class
  }

  private static final Iterable<CodeHealer> CODE_HEALERS = Arrays.asList(
      new CreateMissingClassUnderTest());

  public static void installIn(Project project) {
    CODE_HEALERS.forEach(codeHealer -> codeHealer.installIn(project));
  }

}