package com.mcintosh.iain.core.task.enums;

import java.util.Arrays;
import java.util.Optional;

/**
 * The type of task to perform on the input text.
 */
public enum ParseTaskType {
  REMOVE_VOWELS("remove-vowels"),
  COUNT_CONSONANTS("count-consonants"),
  COUNT_SLOW_BIKE("count-slow-bike");

  private final String value;

  ParseTaskType(String value) {
    this.value = value;
  }

  public String getValue() {
    return value;
  }

  public static Optional<ParseTaskType> fromValue(String value) {
    return Arrays.stream(ParseTaskType.values())
        .filter(f -> f.getValue().equalsIgnoreCase(value))
        .findFirst();
  }
}
