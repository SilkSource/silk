/*
 * Copyright (c) 2018 SilkSource.
 */
package io.silksource.silk.testing;

import io.silksource.silk.coding.api.FullyQualifiedName;
import io.silksource.silk.coding.api.Identifier;


/**
 * Something that gets notified of test progress.
 */
public interface TestListener {

  void start(FullyQualifiedName typeName, Identifier methodName);

  void skip(FullyQualifiedName typeName, Identifier methodName);

  void finish(FullyQualifiedName typeName, Identifier methodName, TestResult result);

}
