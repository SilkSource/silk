/*
 * Copyright (c) 2018 SilkSource.
 */
package io.silksource.silk.code.api;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.function.Consumer;


/**
 * Central authority for notifying that something interesting happened and for responding to such
 * events.
 */
public class Events {

  private final Map<Class<?>, Collection<Consumer<?>>> handlersByEventType = new HashMap<>();

  /**
   * Request notification when something interesting happens.
   * @param eventType the type of events that are interesting to the caller
   * @param handler callback that gets invoked whenever an event of the given type happens
   * @param <T> the type of event
   */
  public <T> void listenFor(Class<T> eventType, Consumer<T> handler) {
    Objects.requireNonNull(eventType, "Missing event type");
    Objects.requireNonNull(handler, "Missing handler");
    handlersByEventType.computeIfAbsent(eventType, ignored -> new ArrayList<>()).add(handler);
  }

  /**
   * Notify interested parties that an event occurred.
   * @param event the event that occurred
   * @param <T> the type of event
   */
  @SuppressWarnings("unchecked")
  public <T> void fire(T event) {
    handlersByEventType.entrySet().stream()
        .filter(e -> e.getKey().isAssignableFrom(event.getClass()))
        .map(Entry::getValue)
        .flatMap(Collection::stream)
        .forEach(c -> ((Consumer<T>)c).accept(event));
  }

}
