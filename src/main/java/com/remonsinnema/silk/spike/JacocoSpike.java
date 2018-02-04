package com.remonsinnema.silk.spike;

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

import com.remonsinnema.silk.code.api.Events;
import com.remonsinnema.silk.code.api.FullyQualifiedName;
import com.remonsinnema.silk.code.api.Project;
import com.remonsinnema.silk.code.api.SourceSet;
import com.remonsinnema.silk.code.api.Type;
import com.remonsinnema.silk.code.event.TypeAddedEvent;
import com.remonsinnema.silk.code.heal.CodeHealer;
import com.remonsinnema.silk.code.heal.syntax.CreateMissingClassUnderTest;
import com.remonsinnema.silk.code.inmemory.InMemoryEvents;
import com.remonsinnema.silk.code.inmemory.InMemoryProject;
import com.remonsinnema.silk.code.inmemory.InMemorySourceSet;
import com.remonsinnema.silk.code.inmemory.InMemoryType;


public class JacocoSpike {

  public static void main(String[] args) {
    try {
      new JacocoSpike().run();
    } catch (Exception e) {
      e.printStackTrace();
      System.exit(1);
    }
  }

  private void run() throws Exception {
    Events events = new InMemoryEvents();
    Project project = new InMemoryProject(events);
    SourceSet sourceSet = new InMemorySourceSet(project, "main");
    Type type = new InMemoryType(sourceSet, new FullyQualifiedName(
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
    codeHealer.listenFor(events);
    events.fire(new TypeAddedEvent(type));

    ExecutionDataStore executionData = new ExecutionDataStore();
    SessionInfoStore sessionInfos = new SessionInfoStore();
    data.collect(executionData, sessionInfos, false);
    runtime.shutdown();
    CoverageBuilder coverageBuilder = new CoverageBuilder();
    Analyzer analyzer = new Analyzer(executionData, coverageBuilder);
    try (InputStream classBytes = getClassBytes(targetClassName)) {
      analyzer.analyzeClass(classBytes, targetClassName);
    }
    coverageBuilder.getSourceFiles().forEach(System.out::println);
    for (final IClassCoverage cc : coverageBuilder.getClasses()) {
      System.out.printf("Coverage of class %s%n", cc.getName());

      printCounter("instructions", cc.getInstructionCounter());
      printCounter("branches", cc.getBranchCounter());
      printCounter("lines", cc.getLineCounter());
      printCounter("methods", cc.getMethodCounter());
      printCounter("complexity", cc.getComplexityCounter());

      for (int i = cc.getFirstLine(); i <= cc.getLastLine(); i++) {
        System.out.printf("Line %s: %s%n", Integer.valueOf(i),
            getColor(cc.getLine(i).getStatus()));
      }
    }
  }

  private void printCounter(final String unit, final ICounter counter) {
    final Integer missed = Integer.valueOf(counter.getMissedCount());
    final Integer total = Integer.valueOf(counter.getTotalCount());
    System.out.printf("%s of %s %s missed%n", missed, total, unit);
  }

  private String getColor(final int status) {
    switch (status) {
    case ICounter.NOT_COVERED:
      return "red";
    case ICounter.PARTLY_COVERED:
      return "yellow";
    case ICounter.FULLY_COVERED:
      return "green";
    }
    return "";
  }


  private InputStream getClassBytes(String fullyQualifiedClassName) {
    String resource = '/' + fullyQualifiedClassName.replace('.', '/') + ".class";
    return getClass().getResourceAsStream(resource);
  }

}
