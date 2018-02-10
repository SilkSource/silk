/*
 * Copyright (c) 2018 SilkSource.
 */
package io.silksource.silk.test.ct;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeMap;
import java.util.stream.Collectors;

import org.yaml.snakeyaml.DumperOptions;
import org.yaml.snakeyaml.DumperOptions.FlowStyle;
import org.yaml.snakeyaml.DumperOptions.ScalarStyle;
import org.yaml.snakeyaml.Yaml;

import io.silksource.silk.code.api.FullyQualifiedName;


public class SerializeTestImpactMapToYaml {

  public void save(TestImpactMap testImpactMap, OutputStream output) {
    Map<String, Collection<String>> map = new TreeMap<>();
    testImpactMap.sources().forEach(source ->
        map.put(source.toString(), new ArrayList<>(testImpactMap.testsTouching(source).stream()
          .map(FullyQualifiedName::toString)
          .collect(Collectors.toSet()))));
    try (PrintWriter writer = new PrintWriter(
        new OutputStreamWriter(output, StandardCharsets.UTF_8))) {
      writer.println(new Yaml(dumperOptions()).dump(map));
    }
  }

  private DumperOptions dumperOptions() {
    DumperOptions result = new DumperOptions();
    result.setDefaultFlowStyle(FlowStyle.BLOCK);
    result.setDefaultScalarStyle(ScalarStyle.PLAIN);
    result.setPrettyFlow(true);
    return result;
  }

  public void load(InputStream input, TestImpactMap testImpactMap) {
    Map<String, Collection<String>> map = new Yaml().load(input);
    Set<FullyQualifiedName> testNames = map.values().stream()
        .flatMap(Collection::stream)
        .map(FullyQualifiedName::new)
        .collect(Collectors.toSet());
    testNames.forEach(testName -> {
      Set<FullyQualifiedName> sourcesTouchedByTest = map.entrySet().stream()
          .filter(e -> contains(e.getValue(), testName))
          .map(Entry::getKey)
          .map(FullyQualifiedName::new)
          .collect(Collectors.toSet());
      testImpactMap.testTouches(testName, sourcesTouchedByTest);
    });
  }

  private boolean contains(Collection<String> items, FullyQualifiedName candidate) {
    return items.stream()
        .map(FullyQualifiedName::new)
        .anyMatch(candidate::equals);
  }

}
