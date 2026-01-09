package com.mcintosh.iain.core.task.strategy;

import com.mcintosh.iain.core.util.Alphabet;
import com.mcintosh.iain.core.task.enums.CaseMode;
import com.mcintosh.iain.core.task.processor.CharacterCounter;
import com.mcintosh.iain.core.util.JsonParser;
import java.util.Map;

/**
 * Counts the number of consonants in a given string.
 * <p>
 * This class uses the {@link CharacterCounter} utility to count consonants defined
 * by {@link Alphabet#getConsonants()} and outputs the result as a JSON string.
 * <p>
 * Example usage:
 * <pre>{@code
 * ConsonantCounter counter = new ConsonantCounter();
 * String result = counter.execute("Hello World");
 * // result -> {"h":1,"l":3,"w":1,"r":1,"d":1} (case-insensitive)
 * }</pre>
 *
 * <p>
 * By default, counts are case-insensitive. An overloaded method is available
 * to perform case-sensitive counting by providing a {@link CaseMode}.
 * </p>
 *
 * <p>
 * Note: If the input contains no consonants, the resulting JSON will have all
 * consonants present with a value of 0, depending on the underlying {@link CharacterCounter} behavior.
 * </p>
 */
public final class ConsonantCounter implements ParseTask {

  ConsonantCounter() {}

  /**
   * Counts consonants in the given input string using a default case-insensitive mode.
   *
   * @param input the string to count consonants in
   * @return a JSON string representing the counts of each consonant
   */
  @Override
  public String execute(String input) {
    return execute(input, CaseMode.INSENSITIVE);
  }

  /**
   * Counts consonants in the given input string using the specified {@link CaseMode}.
   *
   * @param input    the string to count consonants in
   * @param caseMode the case sensitivity mode to use
   * @return a JSON string representing the counts of each consonant
   */
  public String execute(String input, CaseMode caseMode) {
    Map<Character, Integer> counts = CharacterCounter.count(input, Alphabet.getConsonants(), caseMode);

    return formatCountMap(counts);
  }

  /**
   * Converts the consonant counts map into a JSON string.
   *
   * @param counts the consonant count map
   * @return a JSON string representing the counts
   */
  private String formatCountMap(Map<Character, Integer> counts) {
    return JsonParser.instance().toJson(counts);
  }
}
