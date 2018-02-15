/*
 * Copyright (c) 2018 SilkSource.
 */
package io.silksource.silk.coding.file;

import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.FieldVisitor;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

import io.silksource.silk.coding.api.FullyQualifiedName;
import io.silksource.silk.coding.api.Type;


public class TypeLoader extends ClassVisitor {

  private static final String CONSTRUCTOR_NAME = "<init>";

  private final Type type;

  public TypeLoader(Type type) {
    super(Opcodes.ASM5);
    this.type = type;
  }

  @Override
  public MethodVisitor visitMethod(int access, String name, String descriptor, String signature,
      String[] exceptions) {
    if (!isSynthetic(name)) {
      type.addMethod(name);
    }
    return super.visitMethod(access, name, descriptor, signature, exceptions);
  }

  private boolean isSynthetic(String name) {
    return CONSTRUCTOR_NAME.equals(name) || name.startsWith("lambda$");
  }

  @Override
  public FieldVisitor visitField(int access, String name, String descriptor, String signature,
      Object value) {
    type.addField(name, FullyQualifiedName.parseTypeDescriptor(descriptor));
    return super.visitField(access, name, descriptor, signature, value);
  }

}
