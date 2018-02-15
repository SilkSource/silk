/*
 * Copyright (c) 2018 SilkSource.
 */
package io.silksource.silk.coding.api;

import java.util.Objects;
import java.util.Optional;


/**
 * Unique identifier consisting of an optional namespace and an identifier that is unique within that namespace.
 */
public class FullyQualifiedName implements Comparable<FullyQualifiedName> {

  private final String fqn;

  public static FullyQualifiedName parse(String value) {
    if (value == null || value.trim().isEmpty()) {
      throw new IllegalArgumentException("Missing fully qualified name");
    }
    for (String part : value.split("\\.")) {
      new Identifier(part);
    }
    return new FullyQualifiedName(value);
  }

  public static FullyQualifiedName parseTypeDescriptor(String descriptor) {
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

  public FullyQualifiedName(FullyQualifiedName namespace, Identifier name) {
    this(Optional.of(namespace), name);
  }

  public FullyQualifiedName(Optional<FullyQualifiedName> namespace, Identifier name) {
    this(namespace.map(fqn -> fqn + ".").orElse("") + Objects.requireNonNull(name, "Missing name"));
  }

  private FullyQualifiedName(String fqn) {
    this.fqn = fqn;
  }

  public FullyQualifiedName(Identifier name) {
    this(Optional.empty(), name);
  }

  /**
   * Returns the name within the namespace.
   * @return the name within the namespace
   */
  public String getSimpleName() {
    int index = fqn.lastIndexOf('.');
    return index < 0 ? fqn : fqn.substring(index + 1);
  }

  /**
   * Returns the optional namespace of the item.
   * @return the optional namespace of the item
   */
  public Optional<FullyQualifiedName> getNamespace() {
    int index = fqn.lastIndexOf('.');
    return index < 0
        ? Optional.empty()
        : Optional.of(fqn.substring(0, index)).map(FullyQualifiedName::new);
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
    return fqn.hashCode();
  }

  @Override
  public boolean equals(Object obj) {
    if (obj instanceof FullyQualifiedName) {
      FullyQualifiedName other = (FullyQualifiedName)obj;
      return fqn.equals(other.fqn);
    }
    return false;
  }

  @Override
  public int compareTo(FullyQualifiedName other) {
    return fqn.compareTo(other.fqn);
  }

  @Override
  public String toString() {
    return fqn;
  }

}
