/*
 * Copyright (c) 2018 SilkSource.
 */
package io.silksource.silk.testing;

import io.silksource.silk.coding.api.FullyQualifiedName;


/**
 * Something that gets notified of test progress.
 */
public interface TestProgressMonitor {

  void start(FullyQualifiedName test);

  void skip(FullyQualifiedName test);

  void finish(FullyQualifiedName test, TestResult result);

}
