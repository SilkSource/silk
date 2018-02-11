/*
 * Copyright (c) 2018 SilkSource.
 */
package io.silksource.silk.coding.api;

import static org.junit.Assert.assertEquals;

import org.junit.Test;


public class WhenFullyQualifyingNames {

  @Test
  public void shouldConvertFromTypeDescriptor() {
    assertTypeDescriptor("Z", "boolean");
    assertTypeDescriptor("C", "char");
    assertTypeDescriptor("B", "byte");
    assertTypeDescriptor("S", "short");
    assertTypeDescriptor("I", "int");
    assertTypeDescriptor("F", "float");
    assertTypeDescriptor("J", "long");
    assertTypeDescriptor("D", "double");
    assertTypeDescriptor("Ljava/lang/Object;", "java.lang.Object");
    assertTypeDescriptor("[I", "int[]");
  }

  private void assertTypeDescriptor(String typeDescriptor, String fqn) {
    assertEquals(typeDescriptor, new FullyQualifiedName(fqn),
        FullyQualifiedName.fromTypeDescriptor(typeDescriptor));
  }

}
