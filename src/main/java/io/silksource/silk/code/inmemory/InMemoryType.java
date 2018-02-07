package io.silksource.silk.code.inmemory;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import io.silksource.silk.code.api.Field;
import io.silksource.silk.code.api.FullyQualifiedName;
import io.silksource.silk.code.api.Member;
import io.silksource.silk.code.api.SourceSet;
import io.silksource.silk.code.api.Type;
import io.silksource.silk.code.event.FieldAddedEvent;


public class InMemoryType implements Type {

  private final SourceSet sourceSet;
  private final FullyQualifiedName name;
  private final List<Field> fields = new ArrayList<>();

  public InMemoryType(SourceSet sourceSet, FullyQualifiedName name) {
    this.sourceSet = sourceSet;
    this.name = name;
  }

  @Override
  public FullyQualifiedName getName() {
    return name;
  }

  @Override
  public SourceSet getSourceSet() {
    return sourceSet;
  }

  @Override
  public Member addField(FullyQualifiedName type, String name) {
    Field result = new InMemoryField(this, type, name);
    fields.add(result);
    getProject().fire(new FieldAddedEvent(result));
    return result;
  }

  @Override
  public List<Field> getFields() {
    return Collections.unmodifiableList(fields);
  }

}
