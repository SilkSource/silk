/*
 * Copyright (c) 2018 SilkSource.
 */
package io.silksource.silk.test.runner;

import static org.junit.Assert.assertTrue;

import java.io.File;
import java.util.Set;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestName;

import io.silksource.silk.coding.api.Method;
import io.silksource.silk.coding.api.Project;
import io.silksource.silk.coding.file.FileBasedProject;
import io.silksource.silk.testing.TestRunner;
import io.silksource.silk.testing.junit.JUnitTestRunner;


public class WhenTestingUsingJUnit {

  @Rule
  public TestName testName = new TestName();
  private final TestRunner testRunner = new JUnitTestRunner();

  @Test
  public void shouldDiscoverTests() {
    Project project = new FileBasedProject(new File("."));

    Set<Method> discoveredTestMethods = testRunner.findTestMethodsIn(project);

    assertTrue("Current test method is not discovered by runner", discoveredTestMethods.stream()
        .filter(this::isCurrentMethod)
        .findAny()
        .isPresent());
  }

  private boolean isCurrentMethod(Method method) {
    return testName.getMethodName().equals(method.getName())
        && getClass().getName().equals(method.getOwningType().getName().toString());
  }

}
