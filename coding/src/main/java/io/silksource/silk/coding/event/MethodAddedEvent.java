/*
 * Copyright (c) 2018 SilkSource.
 */
package io.silksource.silk.coding.event;

import io.silksource.silk.coding.api.Method;


public class MethodAddedEvent extends TypeChangedEvent {

  private final Method method;

  public MethodAddedEvent(Method method) {
    super(method.getOwningType());
    this.method = method;
  }

  public Method getMethod() {
    return method;
  }

}
