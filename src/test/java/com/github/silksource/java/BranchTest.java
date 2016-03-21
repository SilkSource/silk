package com.github.silksource.java;

import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Test;


public class BranchTest {

  @Test
  public void containsSeparateProductionAndTestCode() {
    Branch branch = new Branch();

    SourceSet production = branch.productionCode();
    SourceSet test = branch.testCode();

    assertNotNull("Missing production code", production);
    assertNotNull("Missing test code", test);
    assertNotEquals("Production and test code should be separate", production, test);
  }

}
