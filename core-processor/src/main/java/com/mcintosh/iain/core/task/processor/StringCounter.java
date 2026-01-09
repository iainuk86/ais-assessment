package com.mcintosh.iain.core.task.processor;

import com.mcintosh.iain.core.task.enums.CaseMode;

/**
 * Utility class for counting occurrences of a substring in a string.
 * <p>
 * It supports both case-sensitive and case-insensitive counting via the {@link CaseMode} enum.
 * <p>
 * Example usage:
 * <pre>{@code
 * int count = StringCounter.execute("Slow bike, slow bike", "slow bike", CaseMode.INSENSITIVE);
 * // count -> 2
 * }</pre>
 *
 * <p>
 * Alternate solution: Built-in Java methods may do the job in a cleaner way, but I assumed you
 * would prefer to see the algorithmic processing of the answer.
 * <pre>{@code
 * for (int i = 0; i <= input.length() - searchValue.length(); i++) {
 *   if (input.startsWith(searchValue, i)) {
 *     count++;
 *   }
 * }}</pre>
 * </p>
 *
 * <p>
 * Note: If the input or search string is empty, this method returns 0.
 * </p>
 */
public final class StringCounter {

  private StringCounter() {
    throw new UnsupportedOperationException("Class not instantiable");
  }

  /**
   * Counts the number of occurrences of {@code searchValue} in {@code input}.
   * <p>
   * The matching respects the provided {@link CaseMode}. For example, in
   * {@link CaseMode#INSENSITIVE}, "Slow" will match "slow".
   * </p>
   *
   * @param input       the string to search within
   * @param searchValue the substring to search for
   * @param caseMode    determines if the matching is case-sensitive or case-insensitive
   * @return the total number of matches of {@code searchValue} in {@code input};
   *         0 if {@code searchValue} is empty or not found
   */
  public static int execute(String input, String searchValue, CaseMode caseMode) {
    if (input == null || input.isBlank() || searchValue == null || searchValue.isBlank()) {
      return 0;
    }

    int count = 0;
    int m = searchValue.length();

    // The pointer j will keep track of the number of continuous matching characters
    int j = 0;
    for (int i = 0; i <= input.length() - m; i++) {
      while (j < m && charactersMatch(input.charAt(i + j), searchValue.charAt(j), caseMode)) {
        j++;
      }

      // If j is the length of the search value, a match has been found
      if (j == m) {
        count++;
      }

      // Reset the count of continuous matching characters
      j = 0;
    }

    return count;
  }

  private static boolean charactersMatch(char c1, char c2, CaseMode caseMode) {
    return caseMode.normalise(c1) == caseMode.normalise(c2);
  }
}
