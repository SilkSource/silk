/*
 * Copyright (c) 2018 SilkSource.
 */
package io.silksource.silk.testing.junit;

import java.nio.file.Path;
import java.util.Collection;
import java.util.Collections;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.junit.platform.engine.discovery.DiscoverySelectors;
import org.junit.platform.engine.support.descriptor.ClassSource;
import org.junit.platform.engine.support.descriptor.MethodSource;
import org.junit.platform.launcher.Launcher;
import org.junit.platform.launcher.LauncherDiscoveryRequest;
import org.junit.platform.launcher.TestIdentifier;
import org.junit.platform.launcher.TestPlan;
import org.junit.platform.launcher.core.LauncherDiscoveryRequestBuilder;
import org.junit.platform.launcher.core.LauncherFactory;

import io.silksource.silk.coding.api.FullyQualifiedName;
import io.silksource.silk.coding.api.Identifier;
import io.silksource.silk.coding.api.Method;
import io.silksource.silk.coding.api.Project;
import io.silksource.silk.coding.api.SourceSet;
import io.silksource.silk.testing.TestListener;
import io.silksource.silk.testing.TestRunner;


public class JUnitTestRunner implements TestRunner {

  private final Launcher launcher = LauncherFactory.create();

  @Override
  public Set<Method> findTestMethodsIn(Project project) {
    SourceSet testSources = project.testSources();
    Set<Path> classpathRoots = Collections.singleton(testSources.getCompiledPath());
    TestPlan testPlan = launcher.discover(LauncherDiscoveryRequestBuilder.request()
        .selectors(DiscoverySelectors.selectClasspathRoots(classpathRoots))
    .build());
    Collection<TestIdentifier> testIds = testPlan.getRoots().stream()
        .flatMap(id -> testPlan.getDescendants(id).stream())
        .collect(Collectors.toList());
    return testIds.stream()
        .map(id -> toMethod(testSources, testIds, id))
        .filter(Optional::isPresent)
        .map(Optional::get)
        .collect(Collectors.toSet());
  }

  private Optional<Method> toMethod(SourceSet sourceSet, Collection<TestIdentifier> testIds,
      TestIdentifier testId) {
    return testId.getSource()
        .filter(s -> s instanceof MethodSource)
        .map(MethodSource.class::cast)
        .flatMap(ms -> toMethod(sourceSet, testIds, testId.getParentId().get(), ms));
  }

  private Optional<Method> toMethod(SourceSet sourceSet, Collection<TestIdentifier> testIds,
      String parentId, MethodSource methodSource) {
    return sourceSet.type(typeOf(testIds, parentId, methodSource))
        .flatMap(t -> t.method(new Identifier(methodSource.getMethodName())));
  }

  private FullyQualifiedName typeOf(Collection<TestIdentifier> testIds, String parentId,
      MethodSource methodSource) {
    return FullyQualifiedName.parse(testIds.stream()
        .filter(id -> id.getUniqueId().equals(parentId))
        .map(TestIdentifier::getSource)
        .map(Optional::get)
        .map(ClassSource.class::cast)
        .map(ClassSource::getClassName)
        .findFirst()
        .orElse(methodSource.getClassName()));
  }

  @Override
  public void runTest(Method testMethod, TestListener listener) {
    LauncherDiscoveryRequest request = LauncherDiscoveryRequestBuilder.request()
        .selectors(DiscoverySelectors.selectMethod(fullyQualifiedMethodNameOf(testMethod)))
    .build();
    launcher.execute(request, new TestExecutionListenerAdapter(testMethod, listener));
  }

  private String fullyQualifiedMethodNameOf(Method method) {
    return String.format("%s#%s", method.getOwningType().getName(), method.getName());
  }

}
