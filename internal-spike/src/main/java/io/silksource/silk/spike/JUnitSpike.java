/*
 * Copyright (c) 2018 SilkSource.
 */
package io.silksource.silk.spike;

import java.io.File;
import java.nio.file.Path;
import java.util.Collection;
import java.util.Collections;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.junit.platform.engine.TestExecutionResult;
import org.junit.platform.engine.TestSource;
import org.junit.platform.engine.discovery.DiscoverySelectors;
import org.junit.platform.engine.support.descriptor.ClassSource;
import org.junit.platform.engine.support.descriptor.MethodSource;
import org.junit.platform.launcher.Launcher;
import org.junit.platform.launcher.LauncherDiscoveryRequest;
import org.junit.platform.launcher.TestIdentifier;
import org.junit.platform.launcher.TestPlan;
import org.junit.platform.launcher.core.LauncherDiscoveryRequestBuilder;
import org.junit.platform.launcher.core.LauncherFactory;


public class JUnitSpike {

  public static void main(String[] args) {
    new JUnitSpike().run();
  }

  private final Launcher launcher = LauncherFactory.create();
  private Collection<TestIdentifier> testIds;

  private void run() {
    discoverTests();
    runTests();
  }

  private void discoverTests() {
    File classesDir = new File("classes").getAbsoluteFile();
    Set<Path> classpathRoots = Collections.singleton(classesDir.toPath());
    TestPlan testPlan = launcher.discover(LauncherDiscoveryRequestBuilder.request()
        .selectors(DiscoverySelectors.selectClasspathRoots(classpathRoots))
    .build());
    testIds = testPlan.getRoots().stream()
        .flatMap(id -> testPlan.getDescendants(id).stream())
        .collect(Collectors.toList());
  }

  private void runTests() {
    testIds
        .stream()
        .filter(TestIdentifier::isTest)
        .map(this::getMethod)
        .forEach(tm -> {
          LauncherDiscoveryRequest request = LauncherDiscoveryRequestBuilder.request()
              .selectors(DiscoverySelectors.selectMethod(tm))
          .build();
          launcher.execute(request, new TestFinishedListener(this::print));
        });
  }

  private String getMethod(TestIdentifier id) {
    if (id.getSource().isPresent()) {
      TestSource source = id.getSource().get();
      if (source instanceof MethodSource) {
        MethodSource method = id.getSource().map(MethodSource.class::cast).get();
        ClassSource parent = testIds.stream()
            .filter(testId -> testId.getUniqueId().equals(id.getParentId().get()))
            .map(TestIdentifier::getSource)
            .map(Optional::get)
            .map(ClassSource.class::cast)
            .findFirst()
            .get();
        return String.format("%s#%s", parent.getClassName(), method.getMethodName());
      }
    }
    return id.getDisplayName();
  }

  @SuppressWarnings("PMD.SystemPrintln")
  private void print(TestIdentifier id, TestExecutionResult result) {
    System.out.println(String.format("%s%n  => %s", getMethod(id), result.getStatus()));
  }

}
