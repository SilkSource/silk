/*
 * Copyright (c) 2018 SilkSource.
 */
package io.silksource.silk.testing.runner;

import java.util.Optional;


/**
 * Result of executing a test.
 */
public class TestResult {

  public enum Status { PASSED, FAILED, ABORTED }


  private final Status status;
  private final Optional<Throwable> exception;

  public TestResult(Status status, Optional<Throwable> exception) {
    this.status = status;
    this.exception = exception;
  }

  /**
   * Returns the status of the test.
   * @return the status of the test
   */
  public Status getStatus() {
    return status;
  }

  /**
   * Returns the exception that was thrown by the test, if any.
   * @return the exception that was thrown by the test, if any
   */
  public Optional<Throwable> getException() {
    return exception;
  }

}
