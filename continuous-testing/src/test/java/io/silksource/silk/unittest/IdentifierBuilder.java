/*
 * Copyright (c) 2018 SilkSource.
 */
package io.silksource.silk.unittest;

import java.util.Random;
import java.util.stream.Stream;


public class IdentifierBuilder {

  public static String someIdentifier() {
    return new IdentifierBuilder().build();
  }

  public static String someTypeName() {
    return new IdentifierBuilder().forType().build();
  }

  private static final int MIN_DEFAULT_LENGTH = 3;
  private static final int MAX_DEFAULT_LENGTH = 8;

  private final Random random = new Random();
  private int length = MIN_DEFAULT_LENGTH
      + random.nextInt(MAX_DEFAULT_LENGTH - MIN_DEFAULT_LENGTH + 1);
  private boolean type;

  public IdentifierBuilder ofLength(int newLength) {
    this.length = newLength - 1;
    return this;
  }

  public IdentifierBuilder forType() {
    this.type = true;
    return this;
  }

  public String build() {
    return initialChar() + Stream.generate(() -> nextChar())
        .limit(length)
        .collect(StringBuilder::new,
            (sb,  c) -> sb.append(c.charValue()),
            (sb1, sb2) -> sb1.append(sb2)).toString();
  }

  private char nextChar() {
    return (char)('a' + random.nextInt('z' - 'a' + 1));
  }

  private char initialChar() {
    char result = nextChar();
    if (type) {
      result = Character.toUpperCase(result);
    }
    return result;
  }

}
