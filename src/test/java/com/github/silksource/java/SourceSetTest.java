package com.github.silksource.java;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import org.junit.Test;


public class SourceSetTest {

  private final SourceSet sourceSet = new SourceSet();
  private final String fqn = "com.company.app.Type";

  @Test
  public void createsNewClass() {
    JavaClass type = sourceSet.newClass(fqn);

    assertNotNull("Missing class", type);
    assertEquals("Fully qualified name", fqn, type.fullyQualifiedName());
  }

  @Test
  public void findsAddedClass() throws Exception {
    assertNull("Found non-existing class", sourceSet.findClass(fqn));

    sourceSet.newClass(fqn);
    assertNotNull("Missing class", sourceSet.findClass(fqn));
  }

}
