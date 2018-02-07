package io.silksource.silk.code.heal;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.hasItem;
import static org.junit.Assert.assertThat;

import java.util.ArrayList;
import java.util.Collection;
import java.util.stream.Collectors;

import org.junit.Before;
import org.junit.Test;

import io.silksource.silk.code.api.FullyQualifiedName;
import io.silksource.silk.code.api.Project;
import io.silksource.silk.code.api.SourceSet;
import io.silksource.silk.code.api.SourceSets;
import io.silksource.silk.code.api.Type;
import io.silksource.silk.code.event.FieldAddedEvent;
import io.silksource.silk.code.event.TypeAddedEvent;


public abstract class WhenManipulatingCode {

  protected abstract Project newProject();

  private final Collection<Object> firedEvents = new ArrayList<>();
  private Project project;

  @Before
  public void init() {
    project = newProject();
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
    String sourceSet = SourceSets.MAIN;
    String type = "com.foo.Bar";

    project.sourceSet(sourceSet).addType(new FullyQualifiedName(type));

    assertThat("Type added", project.sourceSet(sourceSet).getTypes().stream()
        .map(t -> t.getName()).collect(Collectors.toList()), hasItem(type));
    assertThat("TypeAddedEvent fired", firedEvent(TypeAddedEvent.class).getType().getName(),
        equalTo(type));
  }

  @Test
  public void shouldAddAFieldToAClass() {
    String typeName = "Baz";
    String fieldName = "gnu";
    String fieldType = "Gnu";
    SourceSet sourceSet = project.sourceSet(SourceSets.TEST);
    Type type = sourceSet.addType(new FullyQualifiedName(typeName));

    type.addField(fieldName, sourceSet.addType(new FullyQualifiedName(fieldType)).getName());

    assertThat("Field added", type.getFields().stream()
        .map(f -> f.getName())
        .collect(Collectors.toList()), hasItem(fieldName));
    assertThat("FieldAddedEvent fired", firedEvent(FieldAddedEvent.class).getField().getName(),
        equalTo(fieldName));
  }

}
