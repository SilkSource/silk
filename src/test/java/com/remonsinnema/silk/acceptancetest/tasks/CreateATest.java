package com.remonsinnema.silk.acceptancetest.tasks;

import com.remonsinnema.silk.acceptancetest.abilities.WriteCode;

import io.silksource.silk.code.api.FullyQualifiedName;
import io.silksource.silk.code.api.SourceSets;
import net.serenitybdd.core.steps.Instrumented;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Performable;
import net.serenitybdd.screenplay.Task;
import net.thucydides.core.annotations.Step;


public class CreateATest implements Task {

  private final String testName;

  @Override
  @Step("{0} adds the test class '#testName'")
  public <T extends Actor> void performAs(T actor) {
    WriteCode.as(actor).sourceSet(SourceSets.TEST).add(new FullyQualifiedName(testName));
  }

  public static Performable named(String testName) {
    return Instrumented.instanceOf(CreateATest.class).withProperties(testName);
  }

  public CreateATest(String testName) {
    this.testName = testName;
  }

}
