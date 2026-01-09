package com.mcintosh.iain.core.task.processor;

import com.mcintosh.iain.core.task.enums.CaseMode;
import java.util.Set;

/**
 * Utility class for removing specified characters from a string.
 * <p>
 * It supports both case-sensitive and case-insensitive removal using the {@link CaseMode} enum.
 * <p>
 * Example usage:
 * <pre>{@code
 * Set<Character> vowels = Set.of('a', 'e', 'i', 'o', 'u');
 * String result = CharacterRemover.execute("Hello World", vowels, CaseMode.INSENSITIVE);
 * // result -> "Hll Wrld"
 * }</pre>
 *
 * <p>
 * Note: The original string is not modified; a new string is returned.
 * Characters are removed based on the normalised case defined by {@link CaseMode}.
 * </p>
 *
 * <p>
 * Alternate approaches, such as using regex (e.g., {@code input.replaceAll("[aeiou]", "")}),
 * are possible. This implementation uses a loop and StringBuilder to avoid the overhead
 * of the regex engine, at the cost of slightly more verbose code.
 * </p>
 */
public final class CharacterRemover {

  private CharacterRemover() {
    throw new UnsupportedOperationException("Class not instantiable");
  }

  /**
   * Removes all characters from the input string that are present in the specified set.
   * <p>
   * Characters are removed according to the specified {@link CaseMode}. For
   * example, in {@link CaseMode#INSENSITIVE}, both 'A' and 'a' will be removed
   * if 'a' is in the set.
   * </p>
   *
   * @param input        the input string to process
   * @param charsToRemove the set of characters to remove
   * @param caseMode     determines if removal is case-sensitive or case-insensitive
   * @return a new string with the specified characters removed; the original string is unchanged
   */
  public static String execute(String input, Set<Character> charsToRemove, CaseMode caseMode) {
    if (input == null || input.isBlank()) {
      return "";
    }

    if (charsToRemove == null || charsToRemove.isEmpty()) {
      return input;
    }

    StringBuilder output = new StringBuilder();

    // Only add characters to the final output if they are not one of the characters to remove
    char c;
    for (int i = 0; i < input.length(); i++) {
      c = caseMode.normalise(input.charAt(i));

      if (!charsToRemove.contains(c)) {
        output.append(input.charAt(i));
      }
    }

    return output.toString();
  }
}
