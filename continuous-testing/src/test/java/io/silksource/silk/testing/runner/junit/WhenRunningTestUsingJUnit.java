/*
 * Copyright (c) 2018 SilkSource.
 */
package io.silksource.silk.testing.runner.junit;

import io.silksource.silk.testing.runner.WhenRunningTests;


public class WhenRunningTestUsingJUnit extends WhenRunningTests {

  @Override
  protected JUnitTestRunner newTestRunner() {
    return new JUnitTestRunner();
  }

}
