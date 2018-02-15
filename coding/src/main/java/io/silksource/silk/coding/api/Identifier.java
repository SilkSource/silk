/*
 * Copyright (c) 2018 SilkSource.
 */
package io.silksource.silk.coding.api;


public class Identifier {

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
  public String toString() {
    return text;
  }

}
