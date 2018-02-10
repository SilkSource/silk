/*
 * Copyright (c) 2018 SilkSource.
 */
package io.silksource.silk.coding.environment;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;


public abstract class AbstractEnvironmentTestCase {

  private Environment environment;

  protected abstract Environment newEnvironment();

  protected abstract String expectedSourceDir();

  protected abstract String expectedCompiledDir();


  @Before
  public void init() {
    environment = newEnvironment();
  }

  @Test
  public void shouldReturnLocationOfSources() {
    assertEquals("Source dir", expectedSourceDir(), environment.getSourceDir());
  }

  @Test
  public void shouldReturnLocationOfCOmpiledSources() {
    assertEquals("Compiled dir", expectedCompiledDir(), environment.getCompiledDir());
  }

}
