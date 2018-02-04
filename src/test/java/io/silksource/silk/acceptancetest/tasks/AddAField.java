package io.silksource.silk.acceptancetest.tasks;

import io.silksource.silk.acceptancetest.abilities.WriteCode;
import io.silksource.silk.code.api.FullyQualifiedName;
import io.silksource.silk.code.api.SourceSets;
import net.serenitybdd.core.steps.Instrumented;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Performable;
import net.serenitybdd.screenplay.Task;
import net.thucydides.core.annotations.Step;


public class AddAField implements Task {

  private final String typeName;
  private final String fieldType;
  private final String fieldName;

  @Override
  @Step("{0} adds the field '#fieldName' of type '#fieldType' to class '#typeName'")
  public <T extends Actor> void performAs(T actor) {
    WriteCode.as(actor)
        .sourceSet(SourceSets.TEST)
        .type(new FullyQualifiedName(typeName))
        .addField(new FullyQualifiedName(fieldType), fieldName);
  }

  public AddAField(String type, String fieldType, String fieldName) {
    this.typeName = type;
    this.fieldType = fieldType;
    this.fieldName = fieldName;
  }

  public static NamedField named(String fieldName) {
    return new NamedField(fieldName);
  }


  public static class NamedField {

    private final String fieldName;

    public NamedField(String fieldName) {
      this.fieldName = fieldName;
    }

    public TypedField ofType(String fieldType) {
      return new TypedField(fieldType, fieldName);
    }

  }


  public static class TypedField {

    private final String fieldType;
    private final String fieldName;

    public TypedField(String fieldType, String fieldName) {
      this.fieldType = fieldType;
      this.fieldName = fieldName;
    }

    public Performable to(String typeName) {
      return Instrumented.instanceOf(AddAField.class).withProperties(typeName, fieldType, fieldName);
    }

  }

}
