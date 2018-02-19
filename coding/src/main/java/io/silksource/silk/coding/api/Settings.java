/*
 * Copyright (c) 2018 SilkSource.
 */
package io.silksource.silk.coding.api;

import java.nio.file.Path;
import java.util.Optional;


/**
 * Key-value pairs of preferences.
 */
public interface Settings {

  /**
   * {@linkplain Path Location} of compiled tests on the file system.
   */
  String COMPILED_TESTS_PATH = "compiled.tests.path";


  /**
   * Returns the value for a given key, if any.
   * @param key the key
   * @param type the expected type of the value
   * @param <T> the type of returned value
   * @return the value for a given key, if any
   */
  <T> Optional<T> get(String key, Class<T> type);

  /**
   * Sets the value for a given key.
   * @param key the key
   * @param value the new value
   */
  void set(String key, Object value);

}
