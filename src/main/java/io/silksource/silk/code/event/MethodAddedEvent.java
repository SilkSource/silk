package io.silksource.silk.code.event;

import io.silksource.silk.code.api.Method;


public class MethodAddedEvent extends TypeChangedEvent {

  private final Method method;

  public MethodAddedEvent(Method method) {
    super(method.getOwnerType());
    this.method = method;
  }

  public Method getMethod() {
    return method;
  }

}
