package com.remonsinnema.silk.code.event;

import com.remonsinnema.silk.code.api.Field;


public class FieldAddedEvent extends TypeChangedEvent {

  private final Field field;

  public FieldAddedEvent(Field field) {
    super(field.getOwnerType());
    this.field = field;
  }

  public Field getField() {
    return field;
  }

}
