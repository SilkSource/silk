/*
 * Copyright (c) 2018 SilkSource.
 */
package io.silksource.silk.test.runner;

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
import org.junit.platform.launcher.TestIdentifier;
import org.junit.platform.launcher.TestPlan;
import org.junit.platform.launcher.core.LauncherDiscoveryRequestBuilder;
import org.junit.platform.launcher.core.LauncherFactory;

import io.silksource.silk.code.api.FullyQualifiedName;
import io.silksource.silk.code.api.Method;
import io.silksource.silk.code.api.Project;
import io.silksource.silk.code.api.SourceSet;
import io.silksource.silk.code.api.SourceSetNames;


public class JUnitTestRunner implements TestRunner {

  private final Launcher launcher = LauncherFactory.create();

  @Override
  public Set<Method> findTestMethodsIn(Project project) {
    Optional<SourceSet> testSources = project.sourceSet(SourceSetNames.TEST.toString());
    if (!testSources.isPresent()) {
      return Collections.emptySet();
    }
    Set<Path> classpathRoots = Collections.singleton(testSources.get().getCompiledPath());
    TestPlan testPlan = launcher.discover(LauncherDiscoveryRequestBuilder.request()
        .selectors(DiscoverySelectors.selectClasspathRoots(classpathRoots))
    .build());
    Collection<TestIdentifier> testIds = testPlan.getRoots().stream()
        .flatMap(id -> testPlan.getDescendants(id).stream())
        .collect(Collectors.toList());
    return testIds.stream()
        .map(id -> toMethod(testSources.get(), testIds, id))
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
        .flatMap(t -> t.method(methodSource.getMethodName()));
  }

  private FullyQualifiedName typeOf(Collection<TestIdentifier> testIds, String parentId,
      MethodSource methodSource) {
    return new FullyQualifiedName(testIds.stream()
        .filter(id -> id.getUniqueId().equals(parentId))
        .map(TestIdentifier::getSource)
        .map(Optional::get)
        .map(ClassSource.class::cast)
        .map(ClassSource::getClassName)
        .findFirst()
        .orElse(methodSource.getClassName()));
  }

}
