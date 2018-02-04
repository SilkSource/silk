package io.silksource.silk.test;

import static io.silksource.silk.unittest.FqnBuilder.someFqn;
import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.TreeSet;

import org.junit.Test;

import io.silksource.silk.code.api.FullyQualifiedName;


public class WhenAnalyzingTheImpactOfTests {

  private final TestImpactMap testImpactMap = new InMemoryTestImpactMap();

  @Test
  public void shouldReportWhichTestsTouchingAGivenSource() {
    FullyQualifiedName testName1 = someFqn();
    FullyQualifiedName testName2 = someFqn();
    FullyQualifiedName sourceName1 = someFqn();
    FullyQualifiedName sourceName2 = someFqn();
    FullyQualifiedName sourceName3 = someFqn();

    testImpactMap.testTouches(testName1, Arrays.asList(sourceName1, sourceName2));
    testImpactMap.testTouches(testName2, Arrays.asList(sourceName1));

    assertIsTouchedBy(sourceName1, testName1, testName2);
    assertIsTouchedBy(sourceName2, testName1);
    assertIsTouchedBy(sourceName3);
  }

  private void assertIsTouchedBy(FullyQualifiedName sourceName, FullyQualifiedName... testNames) {
    assertEquals("Tests touching " + sourceName, new TreeSet<>(Arrays.asList(testNames)),
        testImpactMap.testsTouching(sourceName));
  }

}
