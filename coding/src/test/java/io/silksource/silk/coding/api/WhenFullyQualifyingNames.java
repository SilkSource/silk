/*
 * Copyright (c) 2018 SilkSource.
 */
package io.silksource.silk.coding.api;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import java.util.Optional;

import org.junit.Test;

import io.silksource.silk.testdata.FullyQualifiedNameBuilder;
import io.silksource.silk.testdata.ValueObjectChecker;


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
    assertEquals(typeDescriptor, fqn, FullyQualifiedName.parseTypeDescriptor(typeDescriptor).toString());
  }

  @Test(expected = IllegalArgumentException.class)
  public void shouldNotConvertIncorrectTypeDescriptor() {
    FullyQualifiedName.parseTypeDescriptor("foo");
  }

  @Test
  public void shouldSplitInNameAndNamespace() {
    FullyQualifiedName fqn = FullyQualifiedName.parse("java.lang.Object");
    assertEquals("Name", "Object", fqn.getSimpleName());
    assertEquals("Namespace", "java.lang", fqn.getNamespace().get().toString());
  }

  @Test
  public void shouldNotHaveNamespaceForPrimitiveTypes() {
    FullyQualifiedName fqn = FullyQualifiedName.parse(INT);
    assertEquals("Name", INT, fqn.getSimpleName());
    assertFalse("Should not have namespace", fqn.getNamespace().isPresent());
  }

  @Test
  public void shouldCombineNamespaceAndName() {
    assertEquals("java.lang.String", new FullyQualifiedName(
        FullyQualifiedName.parse("java.lang"), new Identifier("String")).toString());
    assertEquals(INT, new FullyQualifiedName(Optional.empty(), new Identifier(INT)).toString());
  }

  @Test
  public void shouldBeValueObject() {
    FullyQualifiedName object1a = FullyQualifiedNameBuilder.someFullyQualifiedName();
    FullyQualifiedName object1b = FullyQualifiedName.parse(object1a.toString());
    FullyQualifiedName object2 = FullyQualifiedNameBuilder.someFullyQualifiedName();

    new ValueObjectChecker().assertValueObject(object1a, object1b, object2);
  }

}
