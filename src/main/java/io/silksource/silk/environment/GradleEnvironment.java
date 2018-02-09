package io.silksource.silk.environment;


public class GradleEnvironment implements Environment {

  @Override
  public String getSourceDir() {
    return "src";
  }

  @Override
  public String getCompiledDir() {
    return "build/classes/java";
  }

}
