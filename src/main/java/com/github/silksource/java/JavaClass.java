package com.github.silksource.java;

import java.util.LinkedHashMap;
import java.util.Map;


public class JavaClass {

  private final String fqn;
  private final Map<String, String> methodDefinitions = new LinkedHashMap<>();
  private final Map<String, String> methodReturnTypes = new LinkedHashMap<>();

  public JavaClass(String fqn) {
    this.fqn = fqn;
  }

  public String fullyQualifiedName() {
    return fqn;
  }

  public MethodBuilder addMethod(String name, String returnType) {
    methodReturnTypes.put(name, returnType);
    return new MethodBuilder(name, returnType);
  }

  public void setMethod(String name, String definition) {
    methodDefinitions.put(name, definition);
  }

  public MethodBuilder replaceMethod(String name) {
    return new MethodBuilder(name, methodReturnTypes.get(name));
  }

  @Override
  public String toString() {
    return methodDefinitions.toString();
  }


  public class MethodBuilder {

    private final String name;
    private final String returnType;

    public MethodBuilder(String name, String returnType) {
      this.name = name;
      this.returnType = returnType;
    }

    public void addStatement(String format, Object... args) {
      setMethod(name, methodDefinition(String.format(format, args)));
    }

    private String methodDefinition(String body) {
      return String.format("public %s %s() {\n  %s\n}", returnType, name, body);
    }

  }

}
