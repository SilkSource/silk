package io.silksource.silk.test.runner;

import java.util.Collections;
import java.util.Set;

import io.silksource.silk.code.api.Method;
import io.silksource.silk.code.api.Project;


public class JUnitTestRunner implements TestRunner {

  @Override
  public Set<Method> findTestMethodsIn(Project project) {
    return Collections.emptySet();
  }

}
