package io.silksource.silk.acceptancetest.tasks;

import io.silksource.silk.acceptancetest.abilities.WriteCode;
import io.silksource.silk.code.api.FullyQualifiedName;
import io.silksource.silk.code.api.SourceSetNames;
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
    WriteCode.as(actor)
        .sourceSet(SourceSetNames.TEST.toString())
        .get()
        .addType(new FullyQualifiedName(testName));
  }

  public static Performable named(String testName) {
    return Instrumented.instanceOf(CreateATest.class).withProperties(testName);
  }

  public CreateATest(String testName) {
    this.testName = testName;
  }

}
