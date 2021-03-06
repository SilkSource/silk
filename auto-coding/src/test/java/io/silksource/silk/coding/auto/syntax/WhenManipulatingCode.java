/*
 * Copyright (c) 2018 SilkSource.
 */
package io.silksource.silk.coding.auto.syntax;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.hasItem;
import static org.junit.Assert.assertThat;

import java.util.ArrayList;
import java.util.Collection;
import java.util.stream.Collectors;

import org.junit.Before;
import org.junit.Test;

import io.silksource.silk.coding.api.FullyQualifiedName;
import io.silksource.silk.coding.api.Identifier;
import io.silksource.silk.coding.api.Project;
import io.silksource.silk.coding.api.SourceSet;
import io.silksource.silk.coding.api.Type;
import io.silksource.silk.coding.auto.CodeHealers;
import io.silksource.silk.coding.event.FieldAddedEvent;
import io.silksource.silk.coding.event.TypeAddedEvent;


public abstract class WhenManipulatingCode {

  protected abstract Project newProject();

  private final Collection<Object> firedEvents = new ArrayList<>();
  private Project project;

  @Before
  public void init() {
    project = newProject();
    CodeHealers.installIn(project);
    project.getEvents().listenFor(Object.class, e -> firedEvents.add(e));
  }

  private <T> T firedEvent(Class<T> ofType) {
    return firedEvents.stream()
        .filter(e -> ofType.isAssignableFrom(e.getClass()))
        .map(e -> ofType.cast(e))
        .findAny()
        .orElse(null);
  }

  @Test
  public void shouldCreateAClass() {
    FullyQualifiedName type = FullyQualifiedName.parse("com.foo.Bar");
    SourceSet sourceSet = project.mainSources();
    sourceSet.addType(type);

    assertThat("Type added", sourceSet.getTypes().stream()
        .map(t -> t.getName()).collect(Collectors.toList()), hasItem(type));
    assertThat("TypeAddedEvent fired", firedEvent(TypeAddedEvent.class).getType().getName(),
        equalTo(type));
  }

  @Test
  public void shouldAddAFieldToAClass() {
    String typeName = "Baz";
    String fieldName = "gnu";
    String fieldType = "Gnu";
    SourceSet sourceSet = project.testSources();
    Type type = sourceSet.addType(FullyQualifiedName.parse(typeName));

    type.addField(new Identifier(fieldName), sourceSet.addType(FullyQualifiedName.parse(fieldType)).getName());

    assertThat("Field added", type.getFields().stream()
        .map(f -> f.getName().toString())
        .collect(Collectors.toList()), hasItem(fieldName));
    assertThat("FieldAddedEvent fired", firedEvent(FieldAddedEvent.class).getField().getName().toString(),
        equalTo(fieldName));
  }

}
