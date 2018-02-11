/*
 * Copyright (c) 2018 SilkSource.
 */
package io.silksource.silk.testing;

import java.util.Optional;


/**
 * Result of executing a test.
 */
public interface TestResult {

  enum Status { PASSED, FAILED, ABORTED }

  /**
   * Returns the status of the test.
   * @return the status of the test
   */
  Status getStatus();

  /**
   * Returns the exception that was thrown by the test, if any.
   * @return the exception that was thrown by the test, if any
   */
  Optional<Throwable> getException();

}
