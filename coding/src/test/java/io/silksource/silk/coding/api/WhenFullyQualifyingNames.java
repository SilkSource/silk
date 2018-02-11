/*
 * Copyright (c) 2018 SilkSource.
 */
package io.silksource.silk.coding.api;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import java.util.Optional;

import org.junit.Test;


public class WhenFullyQualifyingNames {

  private static final String INT = "int";

  @Test
  public void shouldConvertFromTypeDescriptor() {
    assertTypeDescriptor("Z", "boolean");
    assertTypeDescriptor("C", "char");
    assertTypeDescriptor("B", "byte");
    assertTypeDescriptor("S", "short");
    assertTypeDescriptor("I", INT);
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

  @Test(expected = IllegalArgumentException.class)
  public void shouldNotConvertIncorrectTypeDescriptor() {
    FullyQualifiedName.fromTypeDescriptor("foo");
  }

  @Test
  public void shouldSplitInNameAndNamespace() {
    FullyQualifiedName fqn = new FullyQualifiedName("java.lang.Object");
    assertEquals("Name", "Object", fqn.getSimpleName());
    assertEquals("Namespace", "java.lang", fqn.getNamespace().get().toString());
  }

  @Test
  public void shouldNotHaveNamespaceForPrimitiveTypes() {
    FullyQualifiedName fqn = new FullyQualifiedName(INT);
    assertEquals("Name", INT, fqn.getSimpleName());
    assertFalse("Should not have namespace", fqn.getNamespace().isPresent());
  }

  @Test
  public void shouldCombineNamespaceAndName() {
    assertEquals("java.lang.String", new FullyQualifiedName(new FullyQualifiedName("java.lang"), "String").toString());
    assertEquals(INT, new FullyQualifiedName(Optional.empty(), INT).toString());
  }

}
