package io.silksource.silk.code.api;

import java.util.function.Consumer;


public interface Events {

  <T> void listenFor(Class<T> eventType, Consumer<T> handler);

  void fire(Object event);

}
