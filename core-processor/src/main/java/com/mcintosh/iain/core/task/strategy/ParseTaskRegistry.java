package com.mcintosh.iain.core.task.strategy;

import com.mcintosh.iain.core.task.enums.ParseTaskType;
import java.util.Map;

/**
 * A registry that provides mapping between {@link ParseTaskType} and their corresponding
 * {@link ParseTask} implementations.
 * <p>
 * This class acts as a central lookup for all available {@link ParseTask} strategies.
 * </p>
 *
 * <p>
 * New strategies can be added by updating this class, or by refactoring to use
 * a more dynamic registration mechanism if needed in the future.
 * </p>
 */
public final class ParseTaskRegistry {

  private ParseTaskRegistry() {
    throw new UnsupportedOperationException("Class not instantiable");
  }

  /**
   * Immutable map linking {@link ParseTaskType} to their respective {@link ParseTask} instances.
   */
  private static final Map<ParseTaskType, ParseTask> strategyMap = Map.of(
      ParseTaskType.REMOVE_VOWELS, new VowelRemover(),
      ParseTaskType.COUNT_CONSONANTS, new ConsonantCounter(),
      ParseTaskType.COUNT_SLOW_BIKE, new SlowBikeCounter()
  );

  /**
   * Returns the {@link ParseTask} corresponding to the given {@link ParseTaskType}.
   *
   * @param task the type of parse task; must not be {@code null}
   * @return the corresponding {@link ParseTask} instance, or {@code null} if no mapping exists
   */
  public static ParseTask getStrategy(ParseTaskType task) {
    return strategyMap.get(task);
  }
}
