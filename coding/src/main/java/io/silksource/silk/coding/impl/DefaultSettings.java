/*
 * Copyright (c) 2018 SilkSource.
 */
package io.silksource.silk.coding.impl;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import io.silksource.silk.coding.api.Settings;


public class DefaultSettings implements Settings {

  private final Map<String, Object> valuesByKey = new HashMap<>();

  @Override
  public <T> Optional<T> get(String key, Class<T> type) {
    return Optional.ofNullable(valuesByKey.get(key))
        .map(type::cast);
  }

  @Override
  public void set(String key, Object value) {
    valuesByKey.put(key, value);
  }

}
