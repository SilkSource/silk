package io.silksource.silk.core;

import java.util.Arrays;

import io.silksource.silk.code.api.Project;
import io.silksource.silk.code.heal.CodeHealer;
import io.silksource.silk.code.heal.syntax.CreateMissingClassUnderTest;


public final class Silk {

  private Silk() {
    // Utility class
  }

  private static final Iterable<CodeHealer> CODE_HEALERS = Arrays.asList(
      new CreateMissingClassUnderTest());

  public static void enhance(Project project) {
    CODE_HEALERS.forEach(codeHealer -> codeHealer.installIn(project));
  }

}
