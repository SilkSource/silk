package io.silksource.silk.code.api;

import java.nio.file.Path;
import java.util.List;
import java.util.Optional;


/**
 * Collection of sources that serve the same purpose.
 */
public interface SourceSet extends FileBased {

  /**
   * Returns the name of the source set.
   * @return the name of the source set
   */
  String getName();

  @Override
  default Path getSourcePath() {
    return getProject().getSourcePath().resolve(String.format("src/%s/java", getName()));
  }

  @Override
  default Path getCompiledPath() {
    return getProject().getCompiledPath().resolve(String.format("classes/%s", getName()));
  }

  /**
   * Returns the project that owns this source set.
   * @return the project that owns this source set
   */
  Project getProject();

  /**
   * Add a type to this source set.
   * @param type the name of the type to add
   * @return the added type
   */
  Type addType(FullyQualifiedName type);

  /**
   * Returns the types in this source set.
   * @return the types in this source set
   */
  List<Type> getTypes();

  /**
   * Returns the type with the given name.
   * @param name the name of the type to look for
   * @return the type with the given name, if any
   */
  default Optional<Type> type(FullyQualifiedName name) {
    return getTypes().stream()
        .filter(t -> t.getName().equals(name))
        .findAny();
  }

}
