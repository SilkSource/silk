/*
 * Copyright (c) 2018 SilkSource.
 */
package io.silksource.silk.coding.event;

import io.silksource.silk.coding.api.Type;


public class TypeCompiledEvent extends TypeBasedEvent {

  public TypeCompiledEvent(Type type) {
    super(type);
  }

}
