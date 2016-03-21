package com.github.silksource.java;

import static org.junit.Assert.assertTrue;

import org.junit.Test;


public class JavaClassTest {

  private final JavaClass type = new JavaClass("foo.bar.Baz");
  private final String methodName = "gnu";
  private final String returnType = "void";
  private final String statement = "return;";

  @Test
  public void addsMethodWithStatement() throws Exception {
    type.addMethod(methodName, returnType)
        .addStatement(statement);

    assertMethod(methodName, returnType, statement);
  }

  private void assertMethod(String methodName, String returnType, String statement) {
    String method = String.format("public %s %s() {\n  %s\n}", returnType, methodName, statement);
    assertTrue("Missing method:\n" + method + "\nin:\n" + type, type.toString().contains(method));
  }

  @Test
  public void replacesMethodWithStatement() throws Exception {
    String newStatement = "System.out.println();";
    type.addMethod(methodName, returnType)
        .addStatement(statement);

    type.replaceMethod(methodName)
      .addStatement(newStatement);

    assertMethod(methodName, returnType, newStatement);
  }

}
