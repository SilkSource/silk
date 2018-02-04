package io.silksource.silk.core;

import java.util.Arrays;

import io.silksource.silk.code.api.Project;
import io.silksource.silk.code.heal.CodeHealer;
import io.silksource.silk.code.heal.syntax.CreateMissingClassUnderTest;


public class Silk {

  private static final Iterable<CodeHealer> CODE_HEALERS = Arrays.asList(
      new CreateMissingClassUnderTest());

  public static void enhance(Project project) {
    CODE_HEALERS.forEach(codeHealer -> codeHealer.installIn(project));
  }

}
