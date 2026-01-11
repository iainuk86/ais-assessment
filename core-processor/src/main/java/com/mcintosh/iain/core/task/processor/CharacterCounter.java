package com.mcintosh.iain.core.task.processor;

import com.mcintosh.iain.core.task.enums.CaseMode;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Utility class for counting occurrences of specific characters in a string.
 * <p>
 * It supports both case-sensitive and case-insensitive counting via the {@link CaseMode} enum.
 * <p>
 * Example usage:
 * <pre>{@code
 * Set<Character> charsToCount = Set.of('a', 'b', 'c');
 * Map<Character, Integer> counts = CharacterCounter.count("AbCaabC", charsToCount, CaseMode.INSENSITIVE);
 * // counts -> {'a' = 3, 'b' = 2, 'c' = 2}
 * }</pre>
 *
 * <p>
 * Note: Any character in the input set that does not appear in the string will
 * have a count of 0 in the returned map.
 * </p>
 */
public final class CharacterCounter {
  private static final Logger log = LoggerFactory.getLogger(CharacterCounter.class);

  private CharacterCounter() {
    throw new UnsupportedOperationException("Class not instantiable");
  }

  /**
   * Counts occurrences of the specified characters in the given input string.
   * <p>
   * The returned map contains all characters from {@code charsToCount} as keys.
   * Characters not found in the input string will have a count of 0. The
   * counting respects the provided {@link CaseMode} to handle case sensitivity.
   * </p>
   *
   * @param input       the input string to process
   * @param charsToCount the set of characters to count
   * @param caseMode    determines if counting is case-sensitive or case-insensitive
   * @return a map of characters to their respective counts; all characters in
   *         {@code charsToCount} are guaranteed to appear as keys
   */
  public static Map<Character, Integer> count(
      String input, Set<Character> charsToCount, CaseMode caseMode) {
    log.debug("Beginning character count");
    long startNanos = System.nanoTime();

    if (input == null || charsToCount == null || charsToCount.isEmpty()) {
      return Collections.emptyMap();
    }

    // Initialise the map to a count of 0 for each provided character
    Map<Character, Integer> counts = new HashMap<>();
    for (Character c : charsToCount) {
      counts.put(caseMode.normalise(c), 0);
    }

    // Increment the count if the character is present in the map
    for (int i = 0; i < input.length(); i++) {
      char c = caseMode.normalise(input.charAt(i));
      counts.computeIfPresent(c, (k, v) -> v + 1);
    }

    long elapsedMs = (System.nanoTime() - startNanos) / 1_000_000;
    log.debug("Characters counted in {}ms", elapsedMs);
    return counts;
  }
}
