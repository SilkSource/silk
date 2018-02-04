package io.silksource.silk.acceptancetest.abilities;

import io.silksource.silk.code.api.Project;
import io.silksource.silk.core.Silk;
import net.serenitybdd.screenplay.Ability;
import net.serenitybdd.screenplay.Actor;


public class WriteCode implements Ability {

  private final Project project;

  public WriteCode(Project project) {
    this.project = project;
    Silk.enhance(project);
  }

  public static WriteCode in(Project project) {
    return new WriteCode(project);
  }

  public static Project as(Actor actor) {
    if (actor.abilityTo(WriteCode.class) == null) {
        throw new IllegalArgumentException(actor.getName() + " can't write code");
    }
    return actor.abilityTo(WriteCode.class).project;
}
}
