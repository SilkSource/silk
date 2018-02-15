/*
 * Copyright (c) 2018 SilkSource.
 */
package io.silksource.silk.coding.impl;

import io.silksource.silk.coding.api.Identifier;
import io.silksource.silk.coding.api.Member;
import io.silksource.silk.coding.api.Type;


public class DefaultMember implements Member {

  private final Type ownerType;
  private final Identifier name;

  public DefaultMember(Type ownerType, Identifier name) {
    this.ownerType = ownerType;
    this.name = name;
  }

  @Override
  public Type getOwningType() {
    return ownerType;
  }

  @Override
  public Identifier getName() {
    return name;
  }

  @Override
  public String toString() {
    return String.format("%s.%s", ownerType, name);
  }

}
