/*
 * Copyright (c) 2018 SilkSource.
 */
package io.silksource.silk.coding.event;

import io.silksource.silk.coding.api.Type;


public class TypeLoadedEvent extends TypeBasedEvent {

  public TypeLoadedEvent(Type type) {
    super(type);
  }

}
