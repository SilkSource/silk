

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import org.junit.Test;

import com.github.silksource.java.Branch;
import com.github.silksource.java.JavaClass;
import com.github.silksource.java.SourceSet;


public class WhenOrganizingSourceCode {

  private static final String CLASS_NAME = "com.company.app.Type";

  @Test
  public void shouldSeparateTestCodeFromProductionCode() {
    Branch branch = new Branch();

    SourceSet production = branch.productionCode();
    SourceSet test = branch.testCode();

    assertNotNull("Missing production code", production);
    assertNotNull("Missing test code", test);
    assertNotEquals("Production and test code should be separate", production, test);
  }

  @Test
  public void shouldBreakDownCodeIntoClasses() throws Exception {
    SourceSet sourceSet = new SourceSet();
    assertNull("Found non-existing class", sourceSet.findClass(CLASS_NAME));

    JavaClass type = sourceSet.newClass(CLASS_NAME);

    assertNotNull("Missing class", type);
    assertEquals("Class name", CLASS_NAME, type.fullyQualifiedName());
    assertEquals("Class", type, sourceSet.findClass(CLASS_NAME));
  }

}
