/*
 * Copyright (c) 2018 SilkSource.
 */
package io.silksource.silk.coding.file;

import java.util.Arrays;
import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;

import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.FieldVisitor;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

import io.silksource.silk.coding.api.FullyQualifiedName;
import io.silksource.silk.coding.api.Identifier;
import io.silksource.silk.coding.api.Type;
import io.silksource.silk.coding.event.TypeLoadedEvent;


public class TypeLoader extends ClassVisitor {

  private static final Collection<String> CONSTRUCTOR_NAMES = Arrays.asList("<init>", "<clinit>");

  private final Type type;

  public TypeLoader(Type type) {
    super(Opcodes.ASM5);
    this.type = type;
  }

  @Override
  public void visit(int version, int access, String name, String signature, String superName, String[] interfaces) {
    super.visit(version, access, name, signature, superName, interfaces);
    setSuperType(toFullyQualifiedName(superName));
    setImplementedInterfaces(Arrays.stream(interfaces)
        .map(this::toFullyQualifiedName)
        .collect(Collectors.toSet()));
  }

  private FullyQualifiedName toFullyQualifiedName(String internalName) {
    return FullyQualifiedName.parseTypeDescriptor(String.format("L%s;", internalName));
  }

  private void setSuperType(FullyQualifiedName superType) {
    type.setSuperType(superType);
    loadFromAncestor(superType);
  }

  private void loadFromAncestor(FullyQualifiedName ancestorName) {
    Optional<Type> namedAncestor = findTypeByName(ancestorName);
    if (namedAncestor.isPresent()) {
      loadFromAncestor(namedAncestor.get());
    } else {
      type.getProject().getEvents().listenFor(TypeLoadedEvent.class, event -> {
        if (event.getType().getName().equals(ancestorName)) {
          loadFromAncestor(event.getType());
        }
      });
    }
  }

  private void loadFromAncestor(Type ancestor) {
    ancestor.getFields().forEach(field ->
        type.addField(field.getName(), field.getType()));
    ancestor.getMethods().forEach(method ->
        type.addMethod(method.getName()));
  }

  private Optional<Type> findTypeByName(FullyQualifiedName name) {
    return type.getProject().getSourceSets().stream()
        .map(sourceSet -> sourceSet.type(name))
        .filter(Optional::isPresent)
        .map(Optional::get)
        .findAny();
  }

  private void setImplementedInterfaces(Collection<FullyQualifiedName> interfaces) {
    type.setImplementedInterfaces(interfaces);
    interfaces.forEach(this::loadFromAncestor);
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
