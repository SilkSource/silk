package com.remonsinnema.silk.test;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.SortedSet;
import java.util.TreeSet;

import com.remonsinnema.silk.code.api.FullyQualifiedName;


public class InMemoryTestImpactMap implements TestImpactMap {

  private final Map<FullyQualifiedName, SortedSet<FullyQualifiedName>> sourcesByTouchingTests
      = new HashMap<>();

  @Override
  public void testTouches(FullyQualifiedName testName,
      Collection<FullyQualifiedName> touchedSourceNames) {
    touchedSourceNames.forEach(sourceName -> getTestsTouching(sourceName).add(testName));
  }

  private SortedSet<FullyQualifiedName> getTestsTouching(FullyQualifiedName sourceName) {
    return sourcesByTouchingTests.computeIfAbsent(sourceName, ignored -> new TreeSet<>());
  }

  @Override
  public SortedSet<FullyQualifiedName> testsTouching(FullyQualifiedName sourceName) {
    return Collections.unmodifiableSortedSet(getTestsTouching(sourceName));
  }

}
