package io.silksource.silk.code.api;

import java.io.File;
import java.nio.file.Path;
import java.util.List;
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
   * Add a field to this type.
   * @param name the name of the field to add
   * @param type the type of the field to add
   * @return the added field
   */
  Field addField(String name, FullyQualifiedName type);

  /**
   * Returns all the fields in this type.
   * @return all the fields in this type
   */
  List<Field> getFields();

  /**
   * Returns the field with the given name.
   * @param name the name of the field to look for
   * @return the field with the given name, if any
   */
  default Optional<Field> field(String name) {
    return getFields().stream()
        .filter(s -> s.getName().equals(name))
        .findAny();
  }

  /**
   * Add a method to this type.
   * @param name the name of the method to add
   * @return the added method
   */
  Method addMethod(String name);

  /**
   * Returns all the methods in this type.
   * @return all the methods in this type
   */
  List<Method> getMethods();

  /**
   * Returns the method with the given name.
   * @param name the name of the method to look for
   * @return the method with the given name, if any
   */
  default Optional<Method> method(String name) {
    return getMethods().stream()
        .filter(s -> s.getName().equals(name))
        .findAny();
  }

}
