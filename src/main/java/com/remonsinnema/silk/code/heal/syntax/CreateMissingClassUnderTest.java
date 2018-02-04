package com.remonsinnema.silk.code.heal.syntax;

import java.util.Locale;

import com.remonsinnema.silk.code.api.Events;
import com.remonsinnema.silk.code.api.Field;
import com.remonsinnema.silk.code.api.FullyQualifiedName;
import com.remonsinnema.silk.code.api.SourceSets;
import com.remonsinnema.silk.code.api.Type;
import com.remonsinnema.silk.code.event.FieldAddedEvent;
import com.remonsinnema.silk.code.heal.CodeHealer;


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
    Type ownerType = field.getOwnerType();
    FullyQualifiedName fieldType = field.getType();
    if (isReferenceToClassUnderTest(ownerType, fieldType)) {
      ownerType.getProject().sourceSet(SourceSets.MAIN).add(fieldType);
    }
  }

  private boolean isReferenceToClassUnderTest(Type ownerType, FullyQualifiedName fieldType) {
    String referringTypeName = simpleNameOf(ownerType.getName());
    String referredTypeName = simpleNameOf(fieldType);
    return referringTypeName.contains(referredTypeName);
  }

  private String simpleNameOf(FullyQualifiedName fullyQualifiedName) {
    return fullyQualifiedName.simpleName().toLowerCase(Locale.ENGLISH);
  }

}
