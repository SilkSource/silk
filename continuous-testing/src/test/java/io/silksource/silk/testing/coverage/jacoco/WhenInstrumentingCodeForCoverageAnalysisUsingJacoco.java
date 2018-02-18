/*
 * Copyright (c) 2018 SilkSource.
 */
package io.silksource.silk.testing.coverage.jacoco;

import io.silksource.silk.testing.coverage.CodeInstrumenter;
import io.silksource.silk.testing.coverage.WhenInstrumentingCodeForCoverageAnalysis;


public class WhenInstrumentingCodeForCoverageAnalysisUsingJacoco extends WhenInstrumentingCodeForCoverageAnalysis {

  @Override
  protected CodeInstrumenter newCodeInstrumenter() {
    return new JacocoCodeInstrumenter();
  }

}
