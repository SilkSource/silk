/*
 * Copyright (c) 2018 SilkSource.
 */
package io.silksource.silk.code.api;

import java.util.Objects;
import java.util.Optional;


/**
 * Unique identifier consisting of an optional parent and a name that is unique within that parent.
 */
public class FullyQualifiedName implements Comparable<FullyQualifiedName> {

  private final String name;

  public FullyQualifiedName(FullyQualifiedName parent, String name) {
    this(Optional.of(parent), name);
  }

  public FullyQualifiedName(Optional<FullyQualifiedName> parent, String name) {
    Objects.requireNonNull(name, "Missing name");
    this.name = parent.map(fqn -> fqn + ".").orElse("") + name;
  }

  public FullyQualifiedName(String name) {
    this(Optional.empty(), name);
  }

  /**
   * Returns the name within the parent.
   * @return the name within the parent
   */
  public String getSimpleName() {
    int index = name.lastIndexOf('.');
    return index < 0 ? name : name.substring(index + 1);
  }

  /**
   * Returns the optional parent of the item.
   * @return the optional parent of the item
   */
  public Optional<FullyQualifiedName> getParent() {
    int index = name.lastIndexOf('.');
    return index < 0
        ? Optional.empty()
        : Optional.of(name.substring(0, index)).map(FullyQualifiedName::new);
  }

  @Override
  public int hashCode() {
    return name.hashCode();
  }

  @Override
  public boolean equals(Object obj) {
    if (obj instanceof FullyQualifiedName) {
      FullyQualifiedName other = (FullyQualifiedName)obj;
      return name.equals(other.name);
    }
    return false;
  }

  @Override
  public int compareTo(FullyQualifiedName other) {
    return name.compareTo(other.name);
  }

  @Override
  public String toString() {
    return name;
  }

}
