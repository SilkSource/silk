/*
 * Copyright (c) 2018 SilkSource.
 */
package io.silksource.silk.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import static io.silksource.silk.unittest.FullyQualifiedNameBuilder.someFullyQualifiedName;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.stream.Collectors;

import org.junit.Test;
import org.yaml.snakeyaml.Yaml;

import io.silksource.silk.code.api.FullyQualifiedName;
import io.silksource.silk.test.ct.SerializeTestImpactMapToYaml;
import io.silksource.silk.test.ct.TestImpactMap;


@SuppressWarnings("unchecked")
public class WhenSerializingTestImpactMapsAsYaml {

  private final SerializeTestImpactMapToYaml serializer = new SerializeTestImpactMapToYaml();
  private final TestImpactMap tim = mock(TestImpactMap.class);
  private final FullyQualifiedName sourceName1 = someFullyQualifiedName();
  private final FullyQualifiedName sourceName2 = someFullyQualifiedName();
  private final FullyQualifiedName testName1 = someFullyQualifiedName();
  private final FullyQualifiedName testName2 = someFullyQualifiedName();

  @Test
  public void shouldSaveMapToStream() throws IOException {
    when(tim.sources()).thenReturn(setOf(sourceName1, sourceName2));
    when(tim.testsTouching(sourceName1)).thenReturn(setOf(testName2));
    when(tim.testsTouching(sourceName2)).thenReturn(setOf(testName1, testName2));

    try (ByteArrayOutputStream output = new ByteArrayOutputStream()) {
      serializer.save(tim, output);
      String yaml = new String(output.toByteArray(), StandardCharsets.UTF_8);
      assertFalse("Missing serialization", yaml.isEmpty());

      Map<String, Collection<String>> map = (Map<String, Collection<String>>)new Yaml().load(yaml);
      assertEquals("Sources", stringsOf(sourceName1, sourceName2), map.keySet());
      assertEquals("Tests for source #1", stringsOf(testName2), asSet(map, sourceName1));
      assertEquals("Tests for source #2", stringsOf(testName1, testName2), asSet(map, sourceName2));
    }
  }

  private SortedSet<String> asSet(Map<String, Collection<String>> map, FullyQualifiedName name) {
    return new TreeSet<String>(map.get(name.toString()));
  }

  private SortedSet<FullyQualifiedName> setOf(FullyQualifiedName... names) {
    return new TreeSet<>(Arrays.asList(names));
  }

  private SortedSet<String> stringsOf(FullyQualifiedName... names) {
    return new TreeSet<>(setOf(names).stream()
        .map(FullyQualifiedName::toString)
        .collect(Collectors.toSet()));
  }

  @Test
  public void shouldLoadMapFromStream() throws IOException {
    Map<String, Object> map = new HashMap<>();
    map.put(sourceName1.toString(), stringsOf(testName1));
    map.put(sourceName2.toString(), stringsOf(testName1, testName2));
    String serialization = new Yaml().dump(map);
    try (ByteArrayInputStream input = new ByteArrayInputStream(serialization.getBytes(StandardCharsets.UTF_8))) {
      serializer.load(input, tim);

      verify(tim).testTouches(testName1, setOf(sourceName1, sourceName2));
      verify(tim).testTouches(testName2, setOf(sourceName2));
    }
  }

}
