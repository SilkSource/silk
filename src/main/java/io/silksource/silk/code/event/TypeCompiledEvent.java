package io.silksource.silk.code.event;

import io.silksource.silk.code.api.Type;


public class TypeCompiledEvent {

  private final Type type;

  public TypeCompiledEvent(Type type) {
    this.type = type;
  }

  public Type getType() {
    return type;
  }

}
