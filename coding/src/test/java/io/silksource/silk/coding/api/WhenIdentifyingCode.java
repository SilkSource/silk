/*
 * Copyright (c) 2018 SilkSource.
 */
package io.silksource.silk.coding.api;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import io.silksource.silk.testdata.IdentifierBuilder;
import io.silksource.silk.testdata.ValueObjectChecker;


public class WhenIdentifyingCode {

  @Test
  public void shouldRejectInvalidIdentifiers() {
    assertIdentifier(null, false);
    assertIdentifier(" ", false);
    assertIdentifier("1q", false);
    assertIdentifier("q1\t", false);
    assertIdentifier("q1", true);
}

  private void assertIdentifier(String text, boolean valid) {
    try {
      new Identifier(text);
      assertTrue("Accepted invalid identifier: " + text, valid);
    } catch (IllegalArgumentException e) {
      assertFalse("Rejected valid identifier: " + text, valid);
    }
  }

  @Test
  public void shouldBeValueObject() {
    Identifier object1a = IdentifierBuilder.someIdentifier();
    Identifier object1b = new Identifier(object1a.toString());
    Identifier object2 = IdentifierBuilder.someIdentifier();

    new ValueObjectChecker().assertValueObject(object1a, object1b, object2);
  }

}
