package io.silksource.silk.code.api;

import java.util.Objects;


public class FullyQualifiedName implements Comparable<FullyQualifiedName> {

  private final String name;

  public FullyQualifiedName(FullyQualifiedName parent, String name) {
    this(String.format("%s.%s", Objects.requireNonNull(parent, "Missing parent"),
        Objects.requireNonNull(name, "Missing name")));
  }

  public FullyQualifiedName(String name) {
    this.name = Objects.requireNonNull(name, "Missing name");
  }

  public String simpleName() {
    int index = name.lastIndexOf('.');
    return index < 0 ? name : name.substring(index + 1);
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
    if (obj instanceof String) {
      return toString().equals(obj);
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
