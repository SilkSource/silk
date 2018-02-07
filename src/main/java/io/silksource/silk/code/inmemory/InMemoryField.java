package io.silksource.silk.code.inmemory;

import io.silksource.silk.code.api.Field;
import io.silksource.silk.code.api.FullyQualifiedName;
import io.silksource.silk.code.api.Type;


public class InMemoryField extends InMemoryMember implements Field {

  private final FullyQualifiedName type;

  public InMemoryField(Type ownerType, String name, FullyQualifiedName type) {
    super(ownerType, name);
    this.type = type;
  }

  @Override
  public FullyQualifiedName getType() {
    return type;
  }

}
