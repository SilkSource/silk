/*
 * Copyright (c) 2018 SilkSource.
 */
package io.silksource.silk.coding.file;

import java.util.Arrays;
import java.util.Collection;

import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.FieldVisitor;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

import io.silksource.silk.coding.api.FullyQualifiedName;
import io.silksource.silk.coding.api.Identifier;
import io.silksource.silk.coding.api.Type;


public class TypeLoader extends ClassVisitor {

  private static final Collection<String> CONSTRUCTOR_NAMES = Arrays.asList("<init>", "<clinit>");

  private final Type type;

  public TypeLoader(Type type) {
    super(Opcodes.ASM5);
    this.type = type;
  }

  @Override
  public MethodVisitor visitMethod(int access, String name, String descriptor, String signature,
      String[] exceptions) {
    if (!isSynthetic(name)) {
      type.addMethod(new Identifier(name));
    }
    return super.visitMethod(access, name, descriptor, signature, exceptions);
  }

  private boolean isSynthetic(String name) {
    return CONSTRUCTOR_NAMES.contains(name) || name.startsWith("lambda$");
  }

  @Override
  public FieldVisitor visitField(int access, String name, String descriptor, String signature,
      Object value) {
    type.addField(new Identifier(name), FullyQualifiedName.parseTypeDescriptor(descriptor));
    return super.visitField(access, name, descriptor, signature, value);
  }

}
