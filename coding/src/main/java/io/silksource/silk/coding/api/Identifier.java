/*
 * Copyright (c) 2018 SilkSource.
 */
package io.silksource.silk.coding.api;

import java.util.Objects;


/**
 * Something that uniquely identifies a piece of source code within a context.
 */
public class Identifier implements Comparable<Identifier> {

  private final String text;

  public Identifier(String text) {
    if (!isIdentifier(text)) {
      throw new IllegalArgumentException("Not a valid identifier: " + text);
    }
    this.text = text;
  }

  private boolean isIdentifier(String candidate) {
    if (candidate == null || candidate.trim().isEmpty()) {
      return false;
    }
    if (!Character.isJavaIdentifierStart(candidate.charAt(0))) {
      return false;
    }
    for (int i = 1; i < candidate.length(); i++) {
      if (!Character.isJavaIdentifierPart(candidate.charAt(i))) {
        return false;
      }
    }
    return true;
  }

  @Override
  public boolean equals(Object obj) {
    if (obj instanceof Identifier) {
      Identifier other = (Identifier)obj;
      return Objects.equals(text, other.text);
    }
    return false;
  }

  @Override
  public int hashCode() {
    return Objects.hash(text);
  }

  @Override
  public int compareTo(Identifier other) {
    return text.compareTo(other.text);
  }

  @Override
  public String toString() {
    return text;
  }

}
