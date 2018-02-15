/*
 * Copyright (c) 2018 SilkSource.
 */
package io.silksource.silk.testdata;

import static org.junit.Assert.assertEquals;


public final class ValueObjectChecker {

  @SuppressWarnings("unchecked")
  public <T> void assertValueObject(T object, T same, T... differents) {
    assertValueEqual(object, same, true);
    for (T different : differents) {
      assertValueEqual(object, different, false);
    }
    assertValueEqual(object, 1, false);
  }

  @SuppressWarnings({ "unchecked", "rawtypes" })
  private void assertValueEqual(Object object1, Object object2, boolean same) {
    assertEquals("==", same, object1.equals(object2));
    assertEquals("== inverse", same, object2.equals(object1));
    assertEquals("hash", same, object1.hashCode() == object2.hashCode());
    assertEquals("toString", same, object1.toString().equals(object2.toString()));
    if (object1 instanceof Comparable && object1.getClass().equals(object2.getClass())) {
      Comparable c1 = (Comparable)object1;
      Comparable c2 = (Comparable)object2;
      assertEquals("compare", same, 0 == c1.compareTo(c2));
    }
  }

}
