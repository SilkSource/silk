/*
 * Copyright (c) 2018 SilkSource.
 */
package io.silksource.silk.coding.impl;

import io.silksource.silk.coding.api.Method;
import io.silksource.silk.coding.api.Type;


public class DefaultMethod extends DefaultMember implements Method {

  public DefaultMethod(Type ownerType, String name) {
    super(ownerType, name);
  }

  @Override
  public String toString() {
    return String.format("%s()", super.toString());
  }

}
