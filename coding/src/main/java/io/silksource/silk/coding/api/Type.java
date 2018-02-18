/*
 * Copyright (c) 2018 SilkSource.
 */
package io.silksource.silk.coding.api;

import java.io.File;
import java.nio.file.Path;
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.Optional;


/**
 * Unit of source code.
 */
public interface Type extends FileBased {

  /**
   * Returns the name of this type.
   * @return the name of this type
   */
  FullyQualifiedName getName();

  /**
   * Returns the {@linkplain SourceSet} that owns this type.
   * @return the {@linkplain SourceSet} that owns this type
   */
  SourceSet getSourceSet();

  /**
   * Returns the {@linkplain Project} that owns this type.
   * @return the {@linkplain Project} that owns this type
   */
  default Project getProject() {
    return getSourceSet().getProject();
  }

  default Events getEvents() {
    return getProject().getEvents();
  }

  @Override
  default Path getSourcePath() {
    return getSourceSet().getSourcePath().resolve(nameAsPath() + ".java");
  }

  default String nameAsPath() {
    return getName().toString().replace(".", File.separator);
  }

  @Override
  default Path getCompiledPath() {
    return getSourceSet().getCompiledPath().resolve(nameAsPath() + ".class");
  }

  /**
   * Returns the type from which this type is derived.
   * @return the type from which this type is derived
   */
  FullyQualifiedName getSuperType();

  /**
   * Sets the type from which this type is derived.
   * @param superType the type from which this type is derived
   */
  void setSuperType(FullyQualifiedName superType);

  /**
   * Returns the interfaces that this type implements, if any.
   * @return the interfaces that this type implements, if any
   */
  Collection<FullyQualifiedName> getImplementedInterfaces();

  /**
   * Set the interfaces that this type implements.
   * @param implementedInterfaces the interfaces that this type implements
   */
  void setImplementedInterfaces(Collection<FullyQualifiedName> implementedInterfaces);

  /**
   * Returns the direct ancestors of this type. These are the {@linkplain #getSuperType() super type} and the
   * {@linkplain #getImplementedInterfaces() implemented interfaces}.
   * @return the direct ancestors of this type
   */
  default Collection<FullyQualifiedName> directAncestors() {
    Collection<FullyQualifiedName> result = new LinkedHashSet<>();
    result.add(getSuperType());
    getImplementedInterfaces().forEach(result::add);
    return result;
  }

  /**
   * Add a field to this type.
   * @param name the name of the field to add
   * @param type the type of the field to add
   * @return the added field
   */
  Field addField(Identifier name, FullyQualifiedName type);

  /**
   * Returns all the fields in this type.
   * @return all the fields in this type
   */
  Collection<Field> getFields();

  /**
   * Returns the field with the given name.
   * @param name the name of the field to look for
   * @return the field with the given name, if any
   */
  default Optional<Field> field(Identifier name) {
    return getFields().stream()
        .filter(s -> s.getName().equals(name))
        .findAny();
  }

  /**
   * Add a method to this type.
   * @param name the name of the method to add
   * @return the added method
   */
  Method addMethod(Identifier name);

  /**
   * Returns all the methods in this type.
   * @return all the methods in this type
   */
  Collection<Method> getMethods();

  /**
   * Returns the method with the given name.
   * @param name the name of the method to look for
   * @return the method with the given name, if any
   */
  default Optional<Method> method(Identifier name) {
    return getMethods().stream()
        .filter(s -> s.getName().equals(name))
        .findAny();
  }

  /**
   * Set this type's code as text.
   * @param text the full source code for this type
   */
  void setText(String text);

}
