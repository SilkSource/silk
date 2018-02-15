/*
 * Copyright (c) 2018 SilkSource.
 */
package io.silksource.silk.test.runner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import java.io.File;
import java.util.Optional;
import java.util.Set;

import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestName;
import org.mockito.ArgumentCaptor;

import io.silksource.silk.coding.api.FullyQualifiedName;
import io.silksource.silk.coding.api.Method;
import io.silksource.silk.coding.api.Project;
import io.silksource.silk.coding.api.Type;
import io.silksource.silk.coding.file.FileBasedProject;
import io.silksource.silk.testing.TestListener;
import io.silksource.silk.testing.TestResult;
import io.silksource.silk.testing.TestResult.Status;
import io.silksource.silk.testing.TestRunner;
import io.silksource.silk.testing.junit.JUnitTestRunner;


public class WhenTestingUsingJUnit {

  @Rule
  public TestName testName = new TestName();
  private final TestRunner testRunner = new JUnitTestRunner();
  private final Project project = new FileBasedProject(new File("."));
  private final FullyQualifiedName className = FullyQualifiedName.parse(getClass().getName());

  @Test
  public void shouldDiscoverTests() {
    Set<Method> discoveredTestMethods = testRunner.findTestMethodsIn(project);

    assertTrue("Current test method is not discovered by runner", discoveredTestMethods.stream()
        .filter(this::isCurrentMethod)
        .findAny()
        .isPresent());
  }

  private boolean isCurrentMethod(Method method) {
    return testName.getMethodName().equals(method.getName())
        && className.equals(method.getOwningType().getName());
  }

  @Test
  public void shouldRunTest() {
    Method testMethod = testMethodNamed("shouldDiscoverTests");

    TestListener listener = mock(TestListener.class);
    testRunner.runTest(testMethod, listener);

    verify(listener).start(className, testMethod.getName());
    ArgumentCaptor<TestResult> resultCaptor = ArgumentCaptor.forClass(TestResult.class);
    verify(listener).finish(eq(className), eq(testMethod.getName()),
        resultCaptor.capture());
    TestResult result = resultCaptor.getValue();
    assertEquals("Status", Status.PASSED, result.getStatus());
    assertFalse("Exception", result.getException().isPresent());
  }

  private Method testMethodNamed(String name) {
    Optional<Type> type = project.testSources().type(className);
    assertTrue("Missing type", type.isPresent());
    Type testType = type.get();

    Optional<Method> method = testType.method(name);
    assertTrue("Missing method", type.isPresent());

    return method.get();
  }

  @Ignore("Referenced in test that verifies test methods can be skipped")
  @Test
  public void skipped() {
    // Never called
  }

  @Test
  public void shouldSkipIgnoredTest() {
    Method testMethod = testMethodNamed("skipped");

    TestListener listener = mock(TestListener.class);
    testRunner.runTest(testMethod, listener);

    verify(listener).skip(className, testMethod.getName());
  }

}
