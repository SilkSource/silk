package io.silksource.silk.unittest;

import java.util.Random;
import java.util.stream.Stream;


public class IdentifierBuilder {

  public static String someIdentifier() {
    return new IdentifierBuilder().build();
  }

  private static final int MIN_DEFAULT_LENGTH = 3;
  private static final int MAX_DEFAULT_LENGTH = 8;

  private final Random random = new Random();
  private int length = MIN_DEFAULT_LENGTH
      + random.nextInt(MAX_DEFAULT_LENGTH - MIN_DEFAULT_LENGTH + 1);

  public IdentifierBuilder ofLength(int length) {
    this.length = length;
    return this;
  }

  public String build() {
    return Stream.generate(() -> (char)('a' + random.nextInt('z' - 'a' + 1)))
        .limit(length)
        .collect(StringBuilder::new,
            (sb,  c) -> sb.append(c.charValue()),
            (sb1, sb2) -> sb1.append(sb2)).toString();
  }

}
