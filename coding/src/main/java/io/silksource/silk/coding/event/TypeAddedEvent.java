/*
 * Copyright (c) 2018 SilkSource.
 */
package io.silksource.silk.coding.event;

import io.silksource.silk.coding.api.Type;


public class TypeAddedEvent {

  private final Type type;

  public TypeAddedEvent(Type type) {
    this.type = type;
  }

  public Type getType() {
    return type;
  }

}
