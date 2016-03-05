package com.github.silksource.java;

import org.jbehave.core.annotations.Given;
import org.jbehave.core.annotations.Pending;
import org.jbehave.core.annotations.Then;
import org.jbehave.core.annotations.When;

public class Steps {

  @Given("a test class $fqn")
  @Pending
  public void setUpTest(String fqn) {
  }

  @When("I reference a non-existing class $name from the test")
  @Pending
  public void referenceClassFromTest(String name) {
  }

  @Then("the class $fqn exists")
  @Pending
  public void assertClassExists(String fqn) {
  }

  @Then("the class $fqn does not exist")
  @Pending
  public void assertClassDoesntExist(String fqn) {
  }

  @When("I change the reference from $pre to $post")
  @Pending
  public void renameReference(String pre, String post) {
  }

}
