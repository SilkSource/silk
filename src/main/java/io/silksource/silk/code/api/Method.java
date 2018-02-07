package io.silksource.silk.code.api;

import java.util.List;


public interface Method extends Member {

  Type getReturnType();

  List<Parameter> getParameters();

}
