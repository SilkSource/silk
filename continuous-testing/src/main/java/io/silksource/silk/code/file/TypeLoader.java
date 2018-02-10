/*
 * Copyright (c) 2018 SilkSource.
 */
package io.silksource.silk.code.file;

import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

import io.silksource.silk.code.api.Type;


public class TypeLoader extends ClassVisitor {

  private static final String CONSTRUCTOR_NAME = "<init>";
  private final Type type;

  public TypeLoader(Type type) {
    super(Opcodes.ASM5);
    this.type = type;
  }

  @Override
  public MethodVisitor visitMethod(int access, String name, String description, String signature, String[] exceptions) {
    if (!isSynthetic(name)) {
      type.addMethod(name);
    }
    return super.visitMethod(access, name, description, signature, exceptions);
  }

  private boolean isSynthetic(String name) {
    return CONSTRUCTOR_NAME.equals(name) || name.startsWith("lambda$");
  }

}
