/*
 * Copyright (c) 2018 SilkSource.
 */
package io.silksource.silk.testing;

import io.silksource.silk.coding.api.FullyQualifiedName;


/**
 * Something that gets notified of test progress.
 */
public interface TestListener {

  void start(FullyQualifiedName typeName, String methodName);

  void skip(FullyQualifiedName typeName, String methodName);

  void finish(FullyQualifiedName typeName, String methodName, TestResult result);

}
