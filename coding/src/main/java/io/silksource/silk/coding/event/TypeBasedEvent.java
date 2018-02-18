/*
 * Copyright (c) 2018 SilkSource.
 */
package io.silksource.silk.coding.event;

import io.silksource.silk.coding.api.Type;


public abstract class TypeBasedEvent {

  private final Type type;

  public TypeBasedEvent(Type type) {
    this.type = type;
  }

  public Type getType() {
    return type;
  }

}
