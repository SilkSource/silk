/*
 * Copyright (c) 2018 SilkSource.
 */
package io.silksource.silk.testing.continuous;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import org.junit.Test;

import io.silksource.silk.coding.api.Plugin;
import io.silksource.silk.coding.api.Project;


public class WhenTestingContinuously {

  private final Plugin codeInstrumenter = mock(Plugin.class);
  private final Plugin continuousTester = new ContinuousTester(codeInstrumenter);
  private final Project project = mock(Project.class);

  @Test
  public void shouldInstallTestImpactMapUpdater() {
    continuousTester.installIn(project);

    verify(codeInstrumenter).installIn(project);
  }

}
