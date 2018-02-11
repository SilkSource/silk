/*
 * Copyright (c) 2018 SilkSource.
 */
package io.silksource.silk.coding.heal;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.hasItem;
import static org.junit.Assert.assertThat;

import java.util.ArrayList;
import java.util.Collection;
import java.util.stream.Collectors;

import org.junit.Before;
import org.junit.Test;

import io.silksource.silk.coding.api.FullyQualifiedName;
import io.silksource.silk.coding.api.Project;
import io.silksource.silk.coding.api.SourceSet;
import io.silksource.silk.coding.api.Type;
import io.silksource.silk.coding.event.FieldAddedEvent;
import io.silksource.silk.coding.event.TypeAddedEvent;
import io.silksource.silk.coding.heal.registry.AllHealers;


public abstract class WhenManipulatingCode {

  protected abstract Project newProject();

  private final Collection<Object> firedEvents = new ArrayList<>();
  private Project project;

  @Before
  public void init() {
    project = newProject();
    AllHealers.installIn(project);
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
    FullyQualifiedName type = new FullyQualifiedName("com.foo.Bar");
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
    Type type = sourceSet.addType(new FullyQualifiedName(typeName));

    type.addField(fieldName, sourceSet.addType(new FullyQualifiedName(fieldType)).getName());

    assertThat("Field added", type.getFields().stream()
        .map(f -> f.getName())
        .collect(Collectors.toList()), hasItem(fieldName));
    assertThat("FieldAddedEvent fired", firedEvent(FieldAddedEvent.class).getField().getName(),
        equalTo(fieldName));
  }

}
