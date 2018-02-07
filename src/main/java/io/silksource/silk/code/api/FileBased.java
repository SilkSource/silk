package io.silksource.silk.code.api;

import java.nio.file.Files;
import java.nio.file.Path;


/**
 * Something that is stored on a file system.
 */
public interface FileBased {

  /**
   * Returns the location on the file system where the item is stored in source form.
   * @return the location on the file system where the item is stored in source form
   */
  Path getSourcePath();

  /**
   * Returns the location on the file system where the item is stored in compiled form.
   * @return the location on the file system where the item is stored in compiled form
   */
  Path getCompiledPath();

  default boolean exists() {
    return Files.exists(getSourcePath());
  }

}
