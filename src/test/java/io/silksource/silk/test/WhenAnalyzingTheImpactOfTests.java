package io.silksource.silk.test;

import static io.silksource.silk.unittest.FqnBuilder.someFqn;
import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.Collections;
import java.util.TreeSet;

import org.junit.Test;

import io.silksource.silk.code.api.FullyQualifiedName;
import io.silksource.silk.test.InMemoryTestImpactMap;
import io.silksource.silk.test.TestImpactMap;


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

    assertEquals("Tests touching source #1",
        new TreeSet<>(Arrays.asList(testName1, testName2)),
        testImpactMap.testsTouching(sourceName1));
    assertEquals("Tests touching source #2",
        new TreeSet<>(Arrays.asList(testName1)),
        testImpactMap.testsTouching(sourceName2));
    assertEquals("Tests touching source #3",
        Collections.emptySortedSet(),
        testImpactMap.testsTouching(sourceName3));
  }

}
