/*
 * Copyright (c) 2018 SilkSource.
 */
package io.silksource.silk.testing.coverage;

import static org.junit.Assert.assertTrue;

import static io.silksource.silk.testdata.FullyQualifiedNameBuilder.someFullyQualifiedName;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import io.silksource.silk.coding.api.FullyQualifiedName;
import io.silksource.silk.coding.api.Project;
import io.silksource.silk.coding.api.SourceSetName;
import io.silksource.silk.testdata.ProjectBuilder;


public abstract class WhenInstrumentingCodeForCoverageAnalysis {

  protected abstract CodeInstrumenter newCodeInstrumenter();

  @Rule
  public TemporaryFolder temporaryFolder = new TemporaryFolder();
  private CodeInstrumenter codeInstrumenter;

  private Project project;

  @Before
  public void init() {
    codeInstrumenter = newCodeInstrumenter();
  }

  @Test
  public void shouldCreateInstrumentedVersionsOfCompiledSources() {
    FullyQualifiedName testTypeName = someFullyQualifiedName();
    project = ProjectBuilder.inDirectory(temporaryFolder.getRoot())
        .withTestType(testTypeName)
        .end()
    .build();

    codeInstrumenter.installIn(project);

    String pathToInstrumentedTestType = String.format("%s/instrumented/%s/%s.class",
        project.getScrathPath(), SourceSetName.TEST.id(), testTypeName.getInternalName());
    assertTrue("Instrumented file not created: " + pathToInstrumentedTestType,
        new File(pathToInstrumentedTestType).isFile());
  }

  @Test(expected = FailedToInstrumentTypeException.class)
  public void shouldThrowExceptionWhenInstrumentationFails() {
    codeInstrumenter = new CodeInstrumenter() {
      @Override
      protected void instrument(String name, InputStream input, OutputStream output) throws IOException {
        throw new IOException();
      }
    };
    project = ProjectBuilder.inDirectory(temporaryFolder.getRoot())
        .withTestType(someFullyQualifiedName())
        .end()
    .build();

    codeInstrumenter.installIn(project);
  }

}
