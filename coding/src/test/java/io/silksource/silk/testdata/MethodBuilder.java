/*
 * Copyright (c) 2018 SilkSource.
 */
package io.silksource.silk.testdata;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Collectors;


public class MethodBuilder {

  private static final String NL = System.lineSeparator();

  private final TypeBuilder parent;
  private final String name;
  private final Collection<String> annotations = new ArrayList<>();
  private String body = "";

  public MethodBuilder(TypeBuilder parent, String name) {
    this.parent = parent;
    this.name = name;
  }

  public TypeBuilder end() {
    parent.addMethod(toString());
    return parent;
  }

  public MethodBuilder withAnnotation(String fqn) {
    annotations.add(fqn);
    return this;
  }

  public MethodBuilder withBody(String code) {
    this.body = "    " + Arrays.stream(code.split("\\n"))
        .map(String::trim)
        .collect(Collectors.joining("    " + NL)) + NL;
    return this;
  }

  @Override
  public String toString() {
    StringBuilder result = new StringBuilder(32);
    annotations.forEach(annotation ->
        result.append("  @").append(annotation).append(NL));
    result.append("  public void ").append(name).append("() {").append(NL).append(body).append("  }").append(NL);
    return result.toString();
  }

}
