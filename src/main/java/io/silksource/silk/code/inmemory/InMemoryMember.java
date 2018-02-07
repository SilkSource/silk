package io.silksource.silk.code.inmemory;

import io.silksource.silk.code.api.Member;
import io.silksource.silk.code.api.Type;


public class InMemoryMember implements Member {

  private final Type ownerType;
  private final String name;

  public InMemoryMember(Type ownerType, String name) {
    this.ownerType = ownerType;
    this.name = name;
  }

  @Override
  public Type getOwnerType() {
    return ownerType;
  }

  @Override
  public String getName() {
    return name;
  }

  @Override
  public String toString() {
    return String.format("%s.%s", ownerType, name);
  }

}
