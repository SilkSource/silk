/*
 * Copyright (c) 2018 SilkSource.
 */
package io.silksource.silk.coding.api;

import java.util.Objects;
import java.util.Optional;


/**
 * Unique identifier consisting of an optional namespace and a name that is unique within that
 * namespace.
 */
public class FullyQualifiedName implements Comparable<FullyQualifiedName> {

  private final String name;

  public static FullyQualifiedName fromTypeDescriptor(String descriptor) {
    return new FullyQualifiedName(typeDescriptorToTypeString(descriptor));
  }

  @SuppressWarnings({ "PMD.CyclomaticComplexity", "PMD.StdCyclomaticComplexity" })
  private static String typeDescriptorToTypeString(String descriptor) {
    switch (descriptor.charAt(0)) {
      case 'Z':
        return "boolean";
      case 'C':
        return "char";
      case 'B':
        return "byte";
      case 'S':
        return "short";
      case 'I':
        return "int";
      case 'F':
        return "float";
      case 'J':
        return "long";
      case 'D':
        return "double";
      case 'L':
        return descriptor.substring(1, descriptor.length() - 1).replace('/', '.');
      case '[':
        return typeDescriptorToTypeString(descriptor.substring(1)) + "[]";
      default:
        throw new IllegalArgumentException("Invalid type descriptor: " + descriptor);
    }
  }

  public FullyQualifiedName(FullyQualifiedName namespace, String name) {
    this(Optional.of(namespace), name);
  }

  public FullyQualifiedName(Optional<FullyQualifiedName> namespace, String name) {
    Objects.requireNonNull(name, "Missing name");
    this.name = namespace.map(fqn -> fqn + ".").orElse("") + name;
  }

  public FullyQualifiedName(String name) {
    this(Optional.empty(), name);
  }

  /**
   * Returns the name within the namespace.
   * @return the name within the namespace
   */
  public String getSimpleName() {
    int index = name.lastIndexOf('.');
    return index < 0 ? name : name.substring(index + 1);
  }

  /**
   * Returns the optional namespace of the item.
   * @return the optional namespace of the item
   */
  public Optional<FullyQualifiedName> getNamespace() {
    int index = name.lastIndexOf('.');
    return index < 0
        ? Optional.empty()
        : Optional.of(name.substring(0, index)).map(FullyQualifiedName::new);
  }

  /**
   * Returns the internal name of the type.
   * @return the internal name of the type
   */
  public String getInternalName() {
    return toString().replace('.', '/');
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
