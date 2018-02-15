/*
 * Copyright (c) 2018 SilkSource.
 */
package io.silksource.silk.test.continuously;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Collections;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import io.silksource.silk.coding.api.FullyQualifiedName;
import io.silksource.silk.coding.api.Method;
import io.silksource.silk.coding.api.Project;
import io.silksource.silk.testdata.ProjectBuilder;
import io.silksource.silk.testing.TestRunner;


public class WhenTestingContinuously {

  @Rule
  public TemporaryFolder temporaryFolder = new TemporaryFolder();
  private final TestRunner testRunner = mock(TestRunner.class);
  private final ContinuousTester tester = new ContinuousTester(testRunner);

  @Test
  public void shouldRunAllTestsWhenNoImpactMapIsFound() {
    String testTypeName = "foo.Bar";
    String testMethodName = "shouldSucceed";
    Project project = ProjectBuilder.inDirectory(temporaryFolder.getRoot())
        .withTestType(testTypeName)
            .withTestMethod(testMethodName)
                .withBody("org.junit.Assert.assertEquals(2, 1+1);")
            .end()
        .end()
    .build();
    Method testMethod = project.testSources()
        .type(FullyQualifiedName.parse(testTypeName))
        .flatMap(type -> type.method(testMethodName))
        .get();
    when(testRunner.findTestMethodsIn(project)).thenReturn(Collections.singleton(testMethod));

    tester.test(project);

    verify(testRunner).runTest(eq(testMethod), any());
  }

}
