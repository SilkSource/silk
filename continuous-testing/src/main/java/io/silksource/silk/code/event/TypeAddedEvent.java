/*
 * Copyright (c) 2018 SilkSource.
 */
package io.silksource.silk.code.event;

import io.silksource.silk.code.api.Type;


public class TypeAddedEvent {

  private final Type type;

  public TypeAddedEvent(Type type) {
    this.type = type;
  }

  public Type getType() {
    return type;
  }

}
