/*
 * Copyright (c) 2018 SilkSource.
 */
package io.silksource.silk.coding.impl;

import io.silksource.silk.coding.api.Field;
import io.silksource.silk.coding.api.FullyQualifiedName;
import io.silksource.silk.coding.api.Type;


public class DefaultField extends DefaultMember implements Field {

  private final FullyQualifiedName type;

  public DefaultField(Type ownerType, String name, FullyQualifiedName type) {
    super(ownerType, name);
    this.type = type;
  }

  @Override
  public FullyQualifiedName getType() {
    return type;
  }

}
