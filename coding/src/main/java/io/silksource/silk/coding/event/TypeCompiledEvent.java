/*
 * Copyright (c) 2018 SilkSource.
 */
package io.silksource.silk.coding.event;

import io.silksource.silk.coding.api.Type;


public class TypeCompiledEvent {

  private final Type type;

  public TypeCompiledEvent(Type type) {
    this.type = type;
  }

  public Type getType() {
    return type;
  }

}
