package com.github.silksource.java;

import java.util.LinkedHashMap;
import java.util.Map;


public class SourceSet {

  private final Map<String, JavaClass> classes = new LinkedHashMap<>();

  public JavaClass newClass(String fqn) {
    JavaClass result = new JavaClass(fqn);
    classes.put(fqn, result);
    return result;
  }

  public JavaClass findClass(String fqn) {
    return classes.get(fqn);
  }

}
