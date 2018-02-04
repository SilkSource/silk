package com.remonsinnema.silk.acceptancetest.questions;

import java.util.List;
import java.util.stream.Collectors;

import com.remonsinnema.silk.acceptancetest.abilities.WriteCode;

import io.silksource.silk.code.api.SourceSets;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Question;
import net.serenitybdd.screenplay.annotations.Subject;


public class TheMain implements Question<List<String>> {

  @Override
  @Subject("the main classes")
  public List<String> answeredBy(Actor actor) {
    return WriteCode.as(actor).sourceSet(SourceSets.MAIN).getTypes().stream()
        .map(t -> t.getName().toString())
        .collect(Collectors.toList());
  }

  public static Question<List<String>> classes() {
    return new TheMain();
  }

}
