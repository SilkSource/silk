/*
 * Copyright (c) 2018 SilkSource.
 */
package io.silksource.silk.testing.coverage;

import io.silksource.silk.coding.api.Type;


/**
 * Indicates a failure to instrument code.
 */
public class FailedToInstrumentTypeException extends RuntimeException {

  private static final long serialVersionUID = 3321665010977396850L;

  public FailedToInstrumentTypeException(Type type, Throwable cause) {
    super("Failed to instrument " + type.getName(), cause);
  }

}
