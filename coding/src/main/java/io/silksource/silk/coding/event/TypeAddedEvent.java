/*
 * Copyright (c) 2018 SilkSource.
 */
package io.silksource.silk.coding.event;

import io.silksource.silk.coding.api.Type;


public class TypeAddedEvent extends TypeBasedEvent {

  public TypeAddedEvent(Type type) {
    super(type);
  }

}
