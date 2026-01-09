package com.mcintosh.iain.core.task.strategy;

import com.mcintosh.iain.core.task.enums.CaseMode;
import com.mcintosh.iain.core.task.processor.StringCounter;

/**
 * Counts the number of occurrences of the phrase <code>"slow bike"</code> in a given string.
 * <p>
 * This class uses the {@link StringCounter} utility to perform the count, and outputs the
 * result as a string representing the integer count.
 * </p>
 *
 * <p>
 * By default, counting is case-insensitive. An overloaded method is available to specify
 * case sensitivity using {@link CaseMode}.
 * </p>
 *
 * <p>
 * Example usage:
 * <pre>{@code
 * SlowBikeCounter counter = new SlowBikeCounter();
 * String result = counter.execute("Slow bike goes slow. Another slow bike appears.");
 * // result -> "2" (case-insensitive)
 * }</pre>
 * </p>
 */
public final class SlowBikeCounter implements ParseTask {

  SlowBikeCounter() {}

  /**
   * Counts occurrences of the phrase "slow bike" in the given input string using
   * a default case-insensitive mode.
   *
   * @param input the string to search
   * @return a string representing the number of occurrences of "slow bike"
   */
  @Override
  public String execute(String input) {
    return execute(input, CaseMode.INSENSITIVE);
  }

  /**
   * Counts occurrences of the phrase "slow bike" in the given input string using
   * the specified {@link CaseMode}.
   *
   * @param input    the string to search
   * @param caseMode the case sensitivity mode to use
   * @return a string representing the number of occurrences of "slow bike"
   */
  public String execute(String input, CaseMode caseMode) {
    return String.valueOf(StringCounter.execute(input, "slow bike", caseMode));
  }

}
