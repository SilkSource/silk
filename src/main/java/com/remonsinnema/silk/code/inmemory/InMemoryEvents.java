package com.remonsinnema.silk.code.inmemory;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;
import java.util.function.Consumer;

import com.remonsinnema.silk.code.api.Events;


public class InMemoryEvents implements Events {

  private final Collection<Handler> handlers = new ArrayList<>();

  @Override
  public <T> void listenFor(Class<T> eventType, Consumer<T> handler) {
    Objects.requireNonNull(handler);
    handlers.add(new Handler(eventType, handler));
  }

  @Override
  public void fire(Object event) {
    handlers.stream()
        .filter(h -> h.eventType.isAssignableFrom(event.getClass()))
        .forEach(h -> h.handler.accept(event));
  }


  static class Handler {

    private final Class<?> eventType;
    private final Consumer<Object> handler;

    @SuppressWarnings("unchecked")
    <T> Handler(Class<T> eventType, Consumer<T> handler) {
      this.eventType = eventType;
      this.handler = (Consumer<Object>)handler;
    }

  }

}
