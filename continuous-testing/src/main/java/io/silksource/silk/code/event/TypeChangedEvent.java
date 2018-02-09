package io.silksource.silk.code.event;

import io.silksource.silk.code.api.Type;


public class TypeChangedEvent {

  private final Type type;

  public TypeChangedEvent(Type type) {
    this.type = type;
  }

  public Type getType() {
    return type;
  }

}
