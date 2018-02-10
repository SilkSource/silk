/*
 * Copyright (c) 2018 SilkSource.
 */
package io.silksource.silk.code.heal.syntax;

import static net.serenitybdd.screenplay.GivenWhenThen.givenThat;
import static net.serenitybdd.screenplay.GivenWhenThen.seeThat;
import static net.serenitybdd.screenplay.GivenWhenThen.then;
import static net.serenitybdd.screenplay.GivenWhenThen.when;

import static org.hamcrest.CoreMatchers.hasItem;
import static org.hamcrest.CoreMatchers.not;

import net.serenitybdd.junit.runners.SerenityRunner;
import net.serenitybdd.screenplay.Actor;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.google.common.io.Files;

import io.silksource.silk.acceptancetest.abilities.WriteCode;
import io.silksource.silk.acceptancetest.questions.TheMain;
import io.silksource.silk.acceptancetest.tasks.AddAField;
import io.silksource.silk.acceptancetest.tasks.CreateATest;
import io.silksource.silk.code.api.Project;
import io.silksource.silk.code.file.FileBasedProject;


@RunWith(SerenityRunner.class)
public class WhenReferringToTheClassUnderTest {

  private static final String TEST_CLASS_NAME = "SetTest";
  private static final String CLASS_UNDER_TEST_NAME = "Set";
  private static final String RANDOM_CLASS_NAME = "Foo";

  private final Actor dave = Actor.named("Dave");
  private final Project hisProject = new FileBasedProject(Files.createTempDir());

  @Before
  public void daveCanWriteCode() {
    dave.can(WriteCode.in(hisProject));
  }

  @Test
  public void referringToTheClassUnderTestShouldCreateThatClass() {
    givenThat(dave).wasAbleTo(CreateATest.named(TEST_CLASS_NAME));
    when(dave).attemptsTo(AddAField.named("set").ofType(CLASS_UNDER_TEST_NAME).to(TEST_CLASS_NAME));
    then(dave).should(seeThat(TheMain.classes(), hasItem(CLASS_UNDER_TEST_NAME)));
  }

  @Test
  public void referringToSomethingOtherThanTheClassUnderTestShouldNotCreateThatClass() {
    givenThat(dave).wasAbleTo(CreateATest.named(TEST_CLASS_NAME));
    when(dave).attemptsTo(AddAField.named("set").ofType(RANDOM_CLASS_NAME).to(TEST_CLASS_NAME));
    then(dave).should(seeThat(TheMain.classes(), not(hasItem(RANDOM_CLASS_NAME))));
  }

}
