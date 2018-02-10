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

import io.silksource.silk.code.api.Method;
import io.silksource.silk.code.api.Project;
import io.silksource.silk.code.file.FileBasedProject;


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
