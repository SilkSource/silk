/*
 * Copyright (c) 2018 SilkSource.
 */
package io.silksource.silk.testing.coverage.jacoco;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.jacoco.core.instr.Instrumenter;
import org.jacoco.core.runtime.IRuntime;
import org.jacoco.core.runtime.SystemPropertiesRuntime;

import io.silksource.silk.testing.coverage.CodeInstrumenter;


/**
 * Plugin that instruments code using JaCoCo.
 */
public class JacocoCodeInstrumenter extends CodeInstrumenter {

  private final IRuntime runtime = new SystemPropertiesRuntime();
  private final Instrumenter instrumenter = new Instrumenter(runtime);

  @Override
  protected void instrument(String name, InputStream input, OutputStream output) throws IOException {
    instrumenter.instrument(input, output, name);
  }

}
