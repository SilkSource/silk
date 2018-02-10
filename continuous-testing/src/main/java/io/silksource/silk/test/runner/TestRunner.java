/*
 * Copyright (c) 2018 SilkSource.
 */
package io.silksource.silk.test.runner;

import java.util.Set;

import io.silksource.silk.code.api.Method;
import io.silksource.silk.code.api.Project;


public interface TestRunner {

  Set<Method> findTestMethodsIn(Project project);

}
