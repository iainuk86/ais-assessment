package com.mcintosh.iain.core.task;

import com.mcintosh.iain.core.util.ArgumentValidator;
import com.mcintosh.iain.core.task.enums.OutputTarget;
import com.mcintosh.iain.core.task.enums.ParseTaskType;
import java.nio.file.Path;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Represents the context for a parsing operation.
 * <p>
 * This record contains all the necessary information for processing a file:
 * the type of task to execute, the input file, the optional output file, and
 * the output target (console or file).
 * </p>
 *
 * <p>
 * Example usage with the builder:
 * <pre>{@code
 * ParseContext context = ParseContext.builder()
 *     .withParseTask("remove-vowels")
 *     .withInputFile("/path/to/input.txt")
 *     .withOutputFile("/path/to/output.txt")
 *     .withOutputTarget("file")
 *     .build();
 * }</pre>
 * </p>
 *
 * <p>
 * All fields are validated through {@link ArgumentValidator} when built using the {@link Builder}.
 * </p>
 *
 * @param parseTaskType the type of parsing task to execute (required)
 * @param inputFile     the path to the input file (required)
 * @param outputFile    the path to the output file (optional)
 * @param outputTarget  the output destination, e.g., console or file (optional, default: console)
 */
public record ParseContext(
    ParseTaskType parseTaskType,
    Path inputFile,
    Path outputFile,
    OutputTarget outputTarget
) {
  private static final Logger log = LoggerFactory.getLogger(ParseContext.class);

  /**
   * Returns a new builder for constructing {@link ParseContext} instances.
   *
   * @return a new {@link Builder} instance
   */
  public static Builder builder() {
    return new Builder();
  }

  /**
   * Builder for creating {@link ParseContext} instances.
   * <p>
   * Performs validation and resolves optional fields (output file and target).
   * </p>
   */
  public static final class Builder {
    private String parseTaskRaw;
    private String inputFileRaw;
    private String outputFileRaw;
    private String outputTargetRaw;

    public Builder withParseTask(String parseTaskRaw) {
      this.parseTaskRaw = parseTaskRaw;
      return this;
    }

    public Builder withInputFile(String inputFileRaw) {
      this.inputFileRaw = inputFileRaw;
      return this;
    }

    public Builder withOutputFile(String outputFileRaw) {
      this.outputFileRaw = outputFileRaw;
      return this;
    }

    public Builder withOutputTarget(String outputTargetRaw) {
      this.outputTargetRaw = outputTargetRaw;
      return this;
    }

    /**
     * Builds a validated {@link ParseContext} instance.
     * <p>
     * Required fields are validated, and optional fields are resolved.
     * The output target defaults to {@link OutputTarget#CONSOLE} if not provided
     * or if the output file is null.
     * </p>
     *
     * @return a new {@link ParseContext} instance
     * @throws IllegalArgumentException if required fields are missing or invalid
     */
    public ParseContext build() {
      // Check required fields
      if (parseTaskRaw == null || parseTaskRaw.isBlank()) {
        log.error("ParseTask not provided");
        throw new IllegalArgumentException("ParseTask is required");
      }
      if (inputFileRaw == null || inputFileRaw.isBlank()) {
        log.error("Input file not provided");
        throw new IllegalArgumentException("Input file is required");
      }

      // Validate provided values
      ParseTaskType parseTaskType = ArgumentValidator.validateParseTask(parseTaskRaw);
      Path inputFile              = ArgumentValidator.validateInputFile(inputFileRaw);

      Path outputFile             = resolveOutputFile(outputFileRaw);
      OutputTarget outputTarget   = resolveOutputTarget(outputTargetRaw, outputFile);

      log.debug("ParseContext built successfully");
      return new ParseContext(parseTaskType, inputFile, outputFile, outputTarget);
    }

    /**
     * Resolves the optional output file.
     *
     * @param outputFileRaw raw output file string
     * @return a validated {@link Path}, or null if not provided
     */
    private Path resolveOutputFile(String outputFileRaw) {
      if (outputFileRaw == null || outputFileRaw.isBlank()) {
        log.debug("Output file not provided");
        return null;
      }

      return ArgumentValidator.validateOrCreateOutputFile(outputFileRaw);
    }

    /**
     * Resolves the optional output target.
     *
     * @param rawTarget the raw target string
     * @param outputFile the resolved output file (may be null)
     * @return the {@link OutputTarget}
     */
    private OutputTarget resolveOutputTarget(String rawTarget, Path outputFile) {
      if (rawTarget == null || rawTarget.isBlank() || outputFile == null) {
        log.debug("No output file target or file specified, printing to console");
        return OutputTarget.CONSOLE;
      }

      return ArgumentValidator.validateOutputTarget(rawTarget);
    }
  }
}
