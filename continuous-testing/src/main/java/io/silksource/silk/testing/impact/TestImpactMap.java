/*
 * Copyright (c) 2018 SilkSource.
 */
package io.silksource.silk.testing.impact;

import java.util.Collection;
import java.util.SortedSet;

import io.silksource.silk.coding.api.FullyQualifiedName;


/**
 * Map of which sources are touched by which tests. A source is touched by a test if at least
 * one line of the source is covered by the test.
 */
public interface TestImpactMap {

  /**
   * Register sources that are touched by a test.
   * @param testName The fully qualified name of a test
   * @param touchedSourceNames The fully qualified names of the sources that are touched by the test
   */
  void testTouches(FullyQualifiedName testName, Collection<FullyQualifiedName> touchedSourceNames);

  /**
   * Returns the tests touching a given source.
   * @param sourceName The fully qualified name of the source
   * @return the fully qualified names of all the tests touching the given source
   */
  SortedSet<FullyQualifiedName> testsTouching(FullyQualifiedName sourceName);

  /**
   * Returns all sources for which test impact information is available.
   * @return all sources for which test impact information is available
   */
  SortedSet<FullyQualifiedName> sources();

}
