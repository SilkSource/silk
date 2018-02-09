package io.silksource.silk.environment;


public class EclipseEnvironment implements Environment {

  @Override
  public String getSourceDir() {
    return "src";
  }

  @Override
  public String getCompiledDir() {
    return "classes";
  }

}
