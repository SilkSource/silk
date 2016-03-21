package com.github.silksource.java;


/**
 * Complete copy of all the source code in a source code control system, including production code and test code.
 *
 * @author ray
 *
 */
public class Branch {

  private final SourceSet production = new SourceSet();
  private final SourceSet test = new SourceSet();

  public SourceSet productionCode() {
    return production;
  }

  public SourceSet testCode() {
    return test;
  }

}
