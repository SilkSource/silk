/*
 * Copyright (c) 2018 SilkSource.
 */
package io.silksource.silk.spike;

import static org.junit.Assert.assertTrue;

import org.junit.Test;


public class WhenRunningTests {

  @Test
  @SuppressWarnings("PMD.UnnecessaryBooleanAssertion")
  public void shouldDiscoverAndRunJUnitTests() {
    assertTrue("Dummy test just to see if it can be discovered and run", true);
  }

}
