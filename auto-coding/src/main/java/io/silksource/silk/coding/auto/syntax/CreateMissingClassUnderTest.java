/*
 * Copyright (c) 2018 SilkSource.
 */
package io.silksource.silk.coding.auto.syntax;

import java.util.Locale;
import java.util.Optional;

import io.silksource.silk.coding.api.Events;
import io.silksource.silk.coding.api.Field;
import io.silksource.silk.coding.api.FullyQualifiedName;
import io.silksource.silk.coding.api.SourceSet;
import io.silksource.silk.coding.api.SourceSetName;
import io.silksource.silk.coding.api.Type;
import io.silksource.silk.coding.auto.CodeHealer;
import io.silksource.silk.coding.event.FieldAddedEvent;


/**
 * Syntactic healer that creates a class that is used but not present.
 */
public class CreateMissingClassUnderTest implements CodeHealer {

  @Override
  public void listenFor(Events events) {
    events.listenFor(FieldAddedEvent.class, this::addMissingClassUnderTest);
  }

  private void addMissingClassUnderTest(FieldAddedEvent event) {
    Field field = event.getField();
    Type ownerType = event.getType();
    FullyQualifiedName fieldType = field.getType();
    if (isReferenceToClassUnderTest(ownerType, fieldType)) {
      mainSourceSet(ownerType).ifPresent(sourceSet -> sourceSet.addType(fieldType));
    }
  }

  private Optional<SourceSet> mainSourceSet(Type ownerType) {
    return ownerType.getProject().sourceSet(SourceSetName.MAIN.id());
  }

  private boolean isReferenceToClassUnderTest(Type ownerType, FullyQualifiedName fieldType) {
    String referringTypeName = simpleNameOf(ownerType.getName());
    String referredTypeName = simpleNameOf(fieldType);
    return referringTypeName.contains(referredTypeName);
  }

  private String simpleNameOf(FullyQualifiedName fullyQualifiedName) {
    return fullyQualifiedName.getSimpleName().toLowerCase(Locale.ENGLISH);
  }

}
