package com.remonsinnema.silk.core;

import java.util.Arrays;

import com.remonsinnema.silk.code.api.Project;
import com.remonsinnema.silk.code.heal.CodeHealer;
import com.remonsinnema.silk.code.heal.syntax.CreateMissingClassUnderTest;


public class Silk {

  private static final Iterable<CodeHealer> CODE_HEALERS = Arrays.asList(
      new CreateMissingClassUnderTest());

  public static void enhance(Project project) {
    CODE_HEALERS.forEach(codeHealer -> codeHealer.installIn(project));
  }

}
