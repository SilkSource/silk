package io.silksource.silk.code.heal.syntax;

import static net.serenitybdd.screenplay.GivenWhenThen.givenThat;
import static net.serenitybdd.screenplay.GivenWhenThen.seeThat;
import static net.serenitybdd.screenplay.GivenWhenThen.then;
import static net.serenitybdd.screenplay.GivenWhenThen.when;
import static org.hamcrest.CoreMatchers.hasItem;
import static org.hamcrest.CoreMatchers.not;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import io.silksource.silk.acceptancetest.abilities.WriteCode;
import io.silksource.silk.acceptancetest.questions.TheMain;
import io.silksource.silk.acceptancetest.tasks.AddAField;
import io.silksource.silk.acceptancetest.tasks.CreateATest;
import io.silksource.silk.code.api.Project;
import io.silksource.silk.code.inmemory.InMemoryProject;
import net.serenitybdd.junit.runners.SerenityRunner;
import net.serenitybdd.screenplay.Actor;


@RunWith(SerenityRunner.class)
public class WhenReferringToTheClassUnderTest {

  private final Actor dave = Actor.named("Dave");
  private final Project hisProject = new InMemoryProject();

  @Before
  public void daveCanWriteCode() {
    dave.can(WriteCode.in(hisProject));
  }

  @Test
  public void referringToTheClassUnderTestShouldCreateThatClass() {
    givenThat(dave).wasAbleTo(CreateATest.named("SetTest"));
    when(dave).attemptsTo(AddAField.named("set").ofType("Set").to("SetTest"));
    then(dave).should(seeThat(TheMain.classes(), hasItem("Set")));
  }

  @Test
  public void referringToSomethingOtherThanTheClassUnderTestShouldNotCreateThatClass() {
    givenThat(dave).wasAbleTo(CreateATest.named("SetTest"));
    when(dave).attemptsTo(AddAField.named("set").ofType("Foo").to("SetTest"));
    then(dave).should(seeThat(TheMain.classes(), not(hasItem("Foo"))));
  }

}
