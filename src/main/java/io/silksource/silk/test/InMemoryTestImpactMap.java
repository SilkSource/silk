package io.silksource.silk.test;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.SortedSet;
import java.util.TreeSet;

import io.silksource.silk.code.api.FullyQualifiedName;


public class InMemoryTestImpactMap implements TestImpactMap {

  private final Map<FullyQualifiedName, SortedSet<FullyQualifiedName>> testsBySourcesTheyTouch
      = new HashMap<>();

  @Override
  public void testTouches(FullyQualifiedName testName,
      Collection<FullyQualifiedName> touchedSourceNames) {
    testsBySourcesTheyTouch.values().forEach(testNames -> testNames.remove(testName));
    touchedSourceNames.forEach(sourceName -> getTestsTouching(sourceName).add(testName));
  }

  private SortedSet<FullyQualifiedName> getTestsTouching(FullyQualifiedName sourceName) {
    return testsBySourcesTheyTouch.computeIfAbsent(sourceName, ignored -> new TreeSet<>());
  }

  @Override
  public SortedSet<FullyQualifiedName> sources() {
    return new TreeSet<>(testsBySourcesTheyTouch.keySet());
  }

  @Override
  public SortedSet<FullyQualifiedName> testsTouching(FullyQualifiedName sourceName) {
    return Collections.unmodifiableSortedSet(getTestsTouching(sourceName));
  }

}
