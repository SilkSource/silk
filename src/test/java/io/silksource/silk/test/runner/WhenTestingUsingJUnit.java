package io.silksource.silk.test.runner;

import static io.silksource.silk.unittest.FullyQualifiedNameBuilder.someFullyQualifiedName;
import static io.silksource.silk.unittest.IdentifierBuilder.someIdentifier;

import org.junit.Test;

import io.silksource.silk.code.api.FullyQualifiedName;
import io.silksource.silk.code.api.Method;
import io.silksource.silk.code.api.Project;
import io.silksource.silk.code.api.SourceSet;
import io.silksource.silk.code.api.SourceSets;
import io.silksource.silk.code.api.Type;
import io.silksource.silk.code.inmemory.InMemoryProject;


public class WhenTestingUsingJUnit {

  private final TestRunner testRunner = new JUnitTestRunner();

  @Test
  public void shouldDiscoverTests() {
    Project project = new InMemoryProject();
    SourceSet sourceSet = project.sourceSet(SourceSets.MAIN);
    Type type = sourceSet.addType(someFullyQualifiedName());
    type.addMethod(someIdentifier());
    Method testMethod = type.addMethod(someIdentifier());
    testMethod.addAnnotation(new FullyQualifiedName(Test.class.getName()));

    /*Set<Method> discoveredTestMethods =*/ testRunner.findTestMethodsIn(project);

    // TODO: Make this work"
    // assertEquals("Test methods", Collections.singleton(testMethod), discoveredTestMethods);
  }

}
