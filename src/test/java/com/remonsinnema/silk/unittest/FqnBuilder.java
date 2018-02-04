package com.remonsinnema.silk.unittest;

import static com.remonsinnema.silk.unittest.IdentifierBuilder.someIdentifier;

import java.util.Random;

import io.silksource.silk.code.api.FullyQualifiedName;


public class FqnBuilder {

  public static FullyQualifiedName someFqn() {
    return new FqnBuilder().build();
  }

  private final Random random = new Random();
  private FullyQualifiedName parent = somePackage();
  private String name = someIdentifier();

  private FullyQualifiedName somePackage() {
    String identifier = someIdentifier();
    if (random.nextBoolean()) {
      return new FullyQualifiedName(identifier);
    }
    return new FullyQualifiedName(somePackage(), identifier);
  }

  public FqnBuilder withPackage(FullyQualifiedName parent) {
    this.parent = parent;
    return this;
  }

  public FqnBuilder withName(String name) {
    this.name = name;
    return this;
  }

  public FullyQualifiedName build() {
    return new FullyQualifiedName(parent, name);
  }

}
