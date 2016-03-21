package integration;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import org.jbehave.core.annotations.Given;
import org.jbehave.core.annotations.Then;
import org.jbehave.core.annotations.When;

import com.github.silksource.java.Branch;
import com.github.silksource.java.JavaClass;


public class Steps {

  private final Branch branch = new Branch();
  private JavaClass test;

  @Given("a test class $fqn")
  public void givenTestClass(String fqn) {
    test = branch.testCode().newClass(fqn);
  }

  @When("I reference a non-existing class $name from the test")
  public void referenceNonExistingClassFrom(String name) {
    test.addMethod("foo", "void")
        .addStatement("%s bar = null;", name);
  }

  @Then("the class $fqn exists")
  public void assertClassExists(String fqn) {
    assertNotNull("Class doesn't exist: " + fqn, findClass(fqn));
  }

  private JavaClass findClass(String fqn) {
    return branch.productionCode().findClass(fqn);
  }

  @Then("the class $fqn does not exist")
  public void assertClassDoesntExist(String fqn) {
    assertNull("Class exists: " + fqn, findClass(fqn));
  }

  @When("I change the reference in the test from $pre to $post")
  public void renameReference(String pre, String post) {
    test.replaceMethod("foo")
        .addStatement("%s bar = null;", post);
  }

}
