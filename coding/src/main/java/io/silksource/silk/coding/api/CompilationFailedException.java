/*
 * Copyright (c) 2018 SilkSource.
 */

package io.silksource.silk.coding.api;


public class CompilationFailedException extends RuntimeException {

  private static final long serialVersionUID = 559584928641197932L;

  public CompilationFailedException(String commandLine, String output) {
    super(String.format("Compilation failed: %s%n%s", commandLine, output));
  }

}
