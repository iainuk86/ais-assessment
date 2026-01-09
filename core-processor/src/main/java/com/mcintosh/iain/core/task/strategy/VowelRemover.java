package com.mcintosh.iain.core.task.strategy;

import com.mcintosh.iain.core.util.Alphabet;
import com.mcintosh.iain.core.task.enums.CaseMode;
import com.mcintosh.iain.core.task.processor.CharacterRemover;

/**
 * Removes vowels from a given string.
 * <p>
 * This class uses the {@link CharacterRemover} utility to remove characters defined
 * by {@link Alphabet#getVowels()} from the input string. The result is returned as a
 * new string with vowels removed.
 * </p>
 *
 * <p>
 * By default, vowel removal is case-insensitive. An overloaded method is available
 * to perform case-sensitive removal using {@link CaseMode}.
 * </p>
 *
 * <p>
 * Example usage:
 * <pre>{@code
 * VowelRemover remover = new VowelRemover();
 * String result = remover.execute("Hello World");
 * // result -> "Hll Wrld" (case-insensitive)
 * }</pre>
 * </p>
 */
public final class VowelRemover implements ParseTask {

  VowelRemover() {}

  /**
   * Removes vowels from the given input string using a default case-insensitive mode.
   *
   * @param input the string from which to remove vowels
   * @return a new string with vowels removed
   */
  @Override
  public String execute(String input) {
    return execute(input, CaseMode.INSENSITIVE);
  }

  /**
   * Removes vowels from the given input string using the specified {@link CaseMode}.
   *
   * @param input    the string from which to remove vowels
   * @param caseMode the case sensitivity mode to use
   * @return a new string with vowels removed
   */
  public String execute(String input, CaseMode caseMode) {
    return CharacterRemover.execute(input, Alphabet.getVowels(), caseMode);
  }
}
