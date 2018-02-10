/*
 * Copyright (c) 2018 SilkSource.
 */
package io.silksource.silk.coding.event;

import io.silksource.silk.coding.api.Field;


public class FieldAddedEvent extends TypeChangedEvent {

  private final Field field;

  public FieldAddedEvent(Field field) {
    super(field.getOwningType());
    this.field = field;
  }

  public Field getField() {
    return field;
  }

}
