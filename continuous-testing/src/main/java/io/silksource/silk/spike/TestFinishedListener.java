package io.silksource.silk.spike;

import java.util.function.BiConsumer;

import org.junit.platform.engine.TestExecutionResult;
import org.junit.platform.launcher.TestExecutionListener;
import org.junit.platform.launcher.TestIdentifier;


public class TestFinishedListener implements TestExecutionListener {

  private final BiConsumer<TestIdentifier, TestExecutionResult> testFinishedConsumer;

  public TestFinishedListener(BiConsumer<TestIdentifier, TestExecutionResult> testFinishedConsumer) {
    this.testFinishedConsumer = testFinishedConsumer;
  }

  @Override
  public void executionFinished(TestIdentifier testIdentifier, TestExecutionResult testExecutionResult) {
    if (testIdentifier.isTest()) {
      testFinishedConsumer.accept(testIdentifier, testExecutionResult);
    }
  }

}
