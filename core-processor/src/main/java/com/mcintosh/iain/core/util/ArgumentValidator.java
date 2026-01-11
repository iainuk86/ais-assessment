package com.mcintosh.iain.core.util;

import com.mcintosh.iain.core.task.enums.OutputTarget;
import com.mcintosh.iain.core.task.enums.ParseTaskType;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Utility class for validating and sanitising arguments used to create a ParseContext.
 */
public final class ArgumentValidator {
  private static final Logger log = LoggerFactory.getLogger(ArgumentValidator.class);

  private ArgumentValidator() {
    throw new UnsupportedOperationException("Class not instantiable");
  }

  /**
   * Validates that the provided input file path exists, is a regular file, and is readable.
   *
   * @param arg the input file path as a string
   * @return the validated {@link Path} to the input file
   * @throws IllegalArgumentException if the file does not exist, is not a regular file, or is not
   * readable
   */
  public static Path validateInputFile(String arg) {
    arg = sanitiseInput(arg);

    Path filePath = Paths.get(arg);
    if (!Files.exists(filePath) || !Files.isRegularFile(filePath) || !Files.isReadable(filePath)) {
      log.debug("Invalid input file");
      throw new IllegalArgumentException("Invalid input file");
    }

    return filePath;
  }

  /**
   * Validates the provided output file path. If the file does not exist, it creates the file
   * and any missing parent directories.
   *
   * @param arg the output file path as a string
   * @return the validated {@link Path} to the output file
   * @throws IllegalArgumentException if the file cannot be created or is not writable
   */
  public static Path validateOrCreateOutputFile(String arg) {
    arg = sanitiseInput(arg);

    Path filePath = Paths.get(arg);
    try {
      // Create parent directories if they do not exist
      Path parentDir = filePath.getParent();
      if (parentDir != null && !Files.exists(parentDir)) {
        log.debug("Parent directories do not exist. Creating them now");
        Files.createDirectories(parentDir);
      }

      // Create the file if it does not exist
      if (!Files.exists(filePath)) {
        log.debug("Creating new output file");
        Files.createFile(filePath);
      }
    } catch (IOException e) {
      log.debug("Invalid output file");
      throw new IllegalArgumentException("Invalid output file", e);
    }

    if (!Files.isWritable(filePath)) {
      log.debug("Output file is not writable");
      throw new IllegalArgumentException("Output file is not writable");
    }

    return filePath;
  }

  /**
   * Validates that the provided argument matches a known {@link OutputTarget}.
   *
   * @param arg the output target string (e.g., "console", "file")
   * @return the corresponding {@link OutputTarget} enum
   * @throws IllegalArgumentException if the argument is not a value OutputTarget value
   */
  public static OutputTarget validateOutputTarget(String arg) {
    arg = sanitiseInput(arg);

    return OutputTarget.fromValue(arg)
        .orElseThrow(() -> new IllegalArgumentException("Invalid output target"));
  }

  /**
   * Validates that the provided argument matches a known {@link ParseTaskType}.
   *
   * @param arg the parse task string (e.g., "remove-vowels")
   * @return the corresponding {@link ParseTaskType} enum
   * @throws IllegalArgumentException if the argument is not a valid ParseTaskType value
   */
  public static ParseTaskType validateParseTask(String arg) {
    arg = sanitiseInput(arg);

    return ParseTaskType.fromValue(arg)
        .orElseThrow(() -> new IllegalArgumentException("Invalid parse task"));
  }

  /**
   * Sanitises input by trimming leading/trailing whitespace and removing surrounding
   * single or double quotes if present.
   *
   * @param input the raw input string
   * @return the sanitised string
   */
  private static String sanitiseInput(String input) {
    String sanitised = input.trim();

    // Remove surrounding quotes if present for extended CLI support
    if ((sanitised.startsWith("\"") && sanitised.endsWith("\"")) ||
        (sanitised.startsWith("'") && sanitised.endsWith("'"))) {
      sanitised = sanitised.substring(1, sanitised.length() - 1).trim();
    }

    return sanitised;
  }
}
