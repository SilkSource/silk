/*
 * Copyright (c) 2018 SilkSource.
 */
package io.silksource.silk.testdata;

import java.util.ArrayList;
import java.util.Collection;

import io.silksource.silk.coding.api.FullyQualifiedName;
import io.silksource.silk.coding.api.Identifier;
import io.silksource.silk.coding.api.SourceSet;
import io.silksource.silk.coding.api.Type;


public class TypeBuilder {

  private static final String NL = System.lineSeparator();

  private final ProjectBuilder parent;
  private final Collection<String> methods = new ArrayList<>();
  private final SourceSet sourceSet;
  private final FullyQualifiedName name;

  public TypeBuilder(ProjectBuilder parent, SourceSet sourceSet, String fqn) {
    this.parent = parent;
    this.sourceSet = sourceSet;
    this.name = FullyQualifiedName.parse(fqn);
  }

  public ProjectBuilder end() {
    Type type = sourceSet.addType(name);
    type.setText(toString());
    return parent;
  }

  public MethodBuilder withMethod(Identifier methodName) {
    return withMethod(methodName.toString());
  }

  public MethodBuilder withMethod(String methodName) {
    return new MethodBuilder(this, methodName);
  }

  public MethodBuilder withTestMethod(Identifier testMethodName) {
    return withTestMethod(testMethodName.toString());
  }

  public MethodBuilder withTestMethod(String testMethodName) {
    return withMethod(testMethodName).withAnnotation("org.junit.Test");
  }

  void addMethod(String method) {
    methods.add(method);
  }

  @Override
  public String toString() {
    StringBuilder result = new StringBuilder();
    name.getNamespace().ifPresent(namespace ->
        result.append("package ").append(namespace).append(';').append(NL).append(NL));
    result.append("public class ").append(name.getSimpleName()).append(" {").append(NL).append(NL);
    methods.forEach(method ->
        result.append(method).append(NL));
    result.append('}').append(NL);
    return result.toString();
  }

}
