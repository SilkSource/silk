/*
 * Copyright (c) 2018 SilkSource.
 */
package io.silksource.silk.testing.junit;

import org.junit.platform.engine.TestExecutionResult;
import org.junit.platform.launcher.TestExecutionListener;
import org.junit.platform.launcher.TestIdentifier;

import io.silksource.silk.coding.api.Method;
import io.silksource.silk.testing.TestListener;
import io.silksource.silk.testing.TestResult;
import io.silksource.silk.testing.TestResult.Status;


class TestExecutionListenerAdapter implements TestExecutionListener {

  private final Method testMethod;
  private final TestListener listener;

  TestExecutionListenerAdapter(Method testMethod, TestListener listener) {
    this.listener = listener;
    this.testMethod = testMethod;
  }

  @Override
  public void executionStarted(TestIdentifier testIdentifier) {
    if (testIdentifier.isTest()) {
      listener.start(testMethod.getOwningType().getName(), testMethod.getName());
    }
  }

  @Override
  public void executionFinished(TestIdentifier testIdentifier,
      TestExecutionResult testExecutionResult) {
    if (!testIdentifier.isTest()) {
      return;
    }
    TestResult result = new TestResult(statusOf(testExecutionResult),
        testExecutionResult.getThrowable());
    listener.finish(testMethod.getOwningType().getName(), testMethod.getName(), result);
  }

  private Status statusOf(TestExecutionResult testExecutionResult) {
    switch (testExecutionResult.getStatus()) {
      case ABORTED:
        return Status.ABORTED;
      case FAILED:
        return Status.FAILED;
      case SUCCESSFUL:
        return Status.PASSED;
      default:
        throw new IllegalArgumentException("Unknown test execution result status "
            + testExecutionResult.getStatus());
    }
  }

  @Override
  public void executionSkipped(TestIdentifier testIdentifier, String reason) {
    listener.skip(testMethod.getOwningType().getName(), testMethod.getName());
  }

}
