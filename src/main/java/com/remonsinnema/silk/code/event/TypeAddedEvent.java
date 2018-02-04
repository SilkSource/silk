package com.remonsinnema.silk.code.event;

import com.remonsinnema.silk.code.api.Type;


public class TypeAddedEvent {

  private final Type type;

  public TypeAddedEvent(Type type) {
    this.type = type;
  }

  public Type getType() {
    return type;
  }

}
