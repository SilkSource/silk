/*
 * Copyright (c) 2018 SilkSource.
 */
package io.silksource.silk.spike;

import java.io.File;
import java.io.InputStream;

import org.jacoco.core.analysis.Analyzer;
import org.jacoco.core.analysis.CoverageBuilder;
import org.jacoco.core.analysis.IClassCoverage;
import org.jacoco.core.analysis.ICounter;
import org.jacoco.core.data.ExecutionDataStore;
import org.jacoco.core.data.SessionInfoStore;
import org.jacoco.core.instr.Instrumenter;
import org.jacoco.core.runtime.IRuntime;
import org.jacoco.core.runtime.RuntimeData;
import org.jacoco.core.runtime.SystemPropertiesRuntime;

import io.silksource.silk.coding.api.FullyQualifiedName;
import io.silksource.silk.coding.api.Project;
import io.silksource.silk.coding.api.SourceSet;
import io.silksource.silk.coding.api.Type;
import io.silksource.silk.coding.event.TypeAddedEvent;
import io.silksource.silk.coding.file.FileBasedProject;
import io.silksource.silk.coding.file.FileBasedSourceSet;
import io.silksource.silk.coding.file.FileBasedType;
import io.silksource.silk.coding.heal.CodeHealer;
import io.silksource.silk.coding.heal.syntax.CreateMissingClassUnderTest;


public class JacocoSpike {

  @SuppressWarnings("PMD.AvoidPrintStackTrace")
  public static void main(String[] args) {
    try {
      new JacocoSpike().run();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  private void run() throws Exception {
    Project project = new FileBasedProject(File.createTempFile("project-", "-spike"));
    SourceSet sourceSet = new FileBasedSourceSet(project, "main");
    Type type = new FileBasedType(sourceSet, FullyQualifiedName.parse(
        CreateMissingClassUnderTest.class.getName()));
    String targetClassName = type.getName().toString();

    IRuntime runtime = new SystemPropertiesRuntime();
    RuntimeData data = new RuntimeData();
    runtime.startup(data);
    Class<?> targetClass;
    try (InputStream classBytes = getClassBytes(targetClassName)) {
      Instrumenter instrumenter = new Instrumenter(runtime);
      byte[] instrumented = instrumenter.instrument(classBytes, targetClassName);
      InMemoryClassLoader memoryClassLoader = new InMemoryClassLoader();
      memoryClassLoader.addDefinition(targetClassName, instrumented);
      targetClass = memoryClassLoader.loadClass(targetClassName);
    }
    CodeHealer codeHealer = (CodeHealer)targetClass.newInstance();
    codeHealer.listenFor(project.getEvents());
    project.getEvents().fire(new TypeAddedEvent(type));

    ExecutionDataStore executionData = new ExecutionDataStore();
    SessionInfoStore sessionInfos = new SessionInfoStore();
    data.collect(executionData, sessionInfos, false);
    runtime.shutdown();
    CoverageBuilder coverageBuilder = new CoverageBuilder();
    Analyzer analyzer = new Analyzer(executionData, coverageBuilder);
    try (InputStream classBytes = getClassBytes(targetClassName)) {
      analyzer.analyzeClass(classBytes, targetClassName);
    }
    coverageBuilder.getSourceFiles().forEach(this::logLine);
    for (final IClassCoverage cc : coverageBuilder.getClasses()) {
      log("Coverage of class %s%n", cc.getName());

      printCounter("instructions", cc.getInstructionCounter());
      printCounter("branches", cc.getBranchCounter());
      printCounter("lines", cc.getLineCounter());
      printCounter("methods", cc.getMethodCounter());
      printCounter("complexity", cc.getComplexityCounter());

      for (int i = cc.getFirstLine(); i <= cc.getLastLine(); i++) {
        log("Line %s: %s%n", Integer.valueOf(i),
            getColor(cc.getLine(i).getStatus()));
      }
    }
  }

  @SuppressWarnings("PMD.SystemPrintln")
  private void logLine(Object message) {
    System.out.println(message);
  }

  @SuppressWarnings("PMD.SystemPrintln")
  private void log(String format, Object... args) {
    System.out.printf(format, args);
  }

  private void printCounter(final String unit, final ICounter counter) {
    final Integer missed = Integer.valueOf(counter.getMissedCount());
    final Integer total = Integer.valueOf(counter.getTotalCount());
    log("%s of %s %s missed%n", missed, total, unit);
  }

  private String getColor(final int status) {
    switch (status) {
      case ICounter.NOT_COVERED:
        return "red";
      case ICounter.PARTLY_COVERED:
        return "yellow";
      case ICounter.FULLY_COVERED:
        return "green";
      default:
        return "";
    }
  }


  private InputStream getClassBytes(String fullyQualifiedClassName) {
    String resource = '/' + fullyQualifiedClassName.replace('.', '/') + ".class";
    return getClass().getResourceAsStream(resource);
  }

}
