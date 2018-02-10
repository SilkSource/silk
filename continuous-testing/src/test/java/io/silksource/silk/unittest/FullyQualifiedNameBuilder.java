/*
 * Copyright (c) 2018 SilkSource.
 */
package io.silksource.silk.unittest;

import static io.silksource.silk.unittest.IdentifierBuilder.someIdentifier;
import static io.silksource.silk.unittest.IdentifierBuilder.someTypeName;

import java.util.Random;

import io.silksource.silk.code.api.FullyQualifiedName;


public class FullyQualifiedNameBuilder {

  public static FullyQualifiedName someFullyQualifiedName() {
    return new FullyQualifiedNameBuilder().build();
  }

  private final Random random = new Random();
  private FullyQualifiedName parent = somePackage();
  private String name = someTypeName();

  private FullyQualifiedName somePackage() {
    String identifier = someIdentifier();
    if (random.nextBoolean()) {
      return new FullyQualifiedName(identifier);
    }
    return new FullyQualifiedName(somePackage(), identifier);
  }

  public FullyQualifiedNameBuilder withPackage(FullyQualifiedName newParent) {
    this.parent = newParent;
    return this;
  }

  public FullyQualifiedNameBuilder withName(String newName) {
    this.name = newName;
    return this;
  }

  public FullyQualifiedName build() {
    return new FullyQualifiedName(parent, name);
  }

}
