/*
 * Copyright (c) 2018 SilkSource.
 */
package io.silksource.silk.testing.continuous;

import io.silksource.silk.coding.api.Plugin;
import io.silksource.silk.coding.api.Project;
import io.silksource.silk.testing.coverage.jacoco.JacocoCodeInstrumenter;


/**
 * Continuously test one or more projects.
 */
public class ContinuousTester implements Plugin {

  private final Plugin codeInstrumenter;

  public ContinuousTester() {
    this(new JacocoCodeInstrumenter());
  }

  ContinuousTester(Plugin codeInstrumenter) {
    this.codeInstrumenter = codeInstrumenter;
  }

  @Override
  public void installIn(Project project) {
    codeInstrumenter.installIn(project);
  }

}
