/*
 * Copyright (c) 2018 SilkSource.
 */
package io.silksource.silk.testing.continuous;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import io.silksource.silk.coding.api.Method;
import io.silksource.silk.coding.api.Project;
import io.silksource.silk.testing.TestRunner;


/**
 * Continuously test one or more projects.
 */
public class ContinuousTester {

  private static final String TEST_IMPACT_MAP_FILE_NAME = "test-impact.yml";

  private final Map<Project, TestImpactMap> testImpactMapsByProject = new HashMap<>();
  private final TestRunner testRunner;

  public ContinuousTester(TestRunner testRunner) {
    this.testRunner = testRunner;
  }

  public void test(Project project) {
    testImpactMapsByProject.put(project, new InMemoryTestImpactMap());
    File testImpactMapFile = new File(project.getScrathPath().toFile(), TEST_IMPACT_MAP_FILE_NAME);
    if (!testImpactMapFile.isFile()) {
      // No test impact analysis performed yet, so run all tests and build up the test impact map
      runAllTests(project);
    }
  }

  private void runAllTests(Project project) {
    testRunner.findTestMethodsIn(project).forEach(this::runTest);
  }

  private void runTest(Method testMethod) {
    testRunner.runTest(testMethod, null);
  }

}
