/*
 * Copyright (c) 2018 SilkSource.
 */
package io.silksource.silk.code.api;


/**
 * Part of a {@linkplain Type}.
 */
public interface Member {

  /**
   * Returns the type that owns this member.
   * @return the type that owns this member
   */
  Type getOwningType();

  /**
   * Returns the name of the member. The name identifies the member within the owning type.
   * @return the name of the member
   */
  String getName();

}
