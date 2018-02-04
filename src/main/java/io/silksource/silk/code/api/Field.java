package io.silksource.silk.code.api;


public interface Field {

  Type getOwnerType();

  String getName();

  FullyQualifiedName getType();

}
