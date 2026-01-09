package com.mcintosh.iain.core.task.enums;

import java.util.Arrays;
import java.util.Optional;

/**
 * Destination where the final output will be printed.
 */
public enum OutputTarget {
  CONSOLE, FILE;

  public static Optional<OutputTarget> fromValue(String value) {
    return Arrays.stream(OutputTarget.values())
        .filter(f -> f.name().equalsIgnoreCase(value))
        .findFirst();
  }
}
