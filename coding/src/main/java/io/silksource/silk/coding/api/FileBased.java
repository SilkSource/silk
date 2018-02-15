/*
 * Copyright (c) 2018 SilkSource.
 */
package io.silksource.silk.coding.api;

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

  /**
   * Returns whether the item exists on the file system.
   * @return whether the item exists on the file system
   */
  default boolean exists() {
    return getSourcePath().toFile().exists();
  }

}
