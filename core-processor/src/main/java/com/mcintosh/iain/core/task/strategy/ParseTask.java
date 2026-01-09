package com.mcintosh.iain.core.task.strategy;

/**
 * Represents a generic task that can be performed on a string input.
 * <p>
 * Implementations of this interface encapsulate specific processing logic, such as
 * removing vowels, counting consonants, or searching for particular phrases.
 * </p>
 *
 * <p>
 * Implementations should define the desired behavior for the {@link #execute(String)} method.
 * </p>
 */
public interface ParseTask {
  String execute(String input);
}
