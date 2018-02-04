package io.silksource.silk.code.inmemory;

import io.silksource.silk.code.api.Field;
import io.silksource.silk.code.api.FullyQualifiedName;
import io.silksource.silk.code.api.Type;


public class InMemoryField implements Field {

  private final Type ownerType;
  private final FullyQualifiedName type;
  private final String name;

  public InMemoryField(Type ownerType, FullyQualifiedName type, String name) {
    this.ownerType = ownerType;
    this.type = type;
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
  public FullyQualifiedName getType() {
    return type;
  }

}
