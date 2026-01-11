package com.mcintosh.iain.cli;

import com.mcintosh.iain.core.task.ParseContext;

/**
 * Utility class for parsing command-line arguments and creating a ParseContext with them.
 * <p>
 * Supported arguments:
 * <ul>
 *   <li>{@code -t} or {@code --task} &mdash; Specifies the task to perform (required).</li>
 *   <li>{@code -i} or {@code --in-file} &mdash; Path to the input file (required).</li>
 *   <li>{@code -o} or {@code --out-file} &mdash; Path to the output file (required).</li>
 *   <li>{@code -d} or {@code --out-dest} &mdash; Output target or destination (required).</li>
 *   <li>{@code -h} or {@code --help} &mdash; Prints usage information.</li>
 * </ul>
 * <p>
 * If an unrecognized argument is provided or a required value is missing, an
 * {@link IllegalArgumentException} is thrown, which is caught and converted to a user-friendly
 * error message to be displayed with the correct usage.
 */
public final class CommandLineParser {

  private CommandLineParser() {
    throw new UnsupportedOperationException("Class not instantiable");
  }

  /**
   * Main entry point of the CLI demo app.
   * Parses the provided command-line arguments and returns a {@link ParseContext}
   * containing the parsed values.
   * <p>
   * If any argument is invalid or missing a required value, usage information
   * is displayed, and {@code null} is returned.
   *
   * @param args the command-line arguments to parse
   * @return a {@link ParseContext} with the parsed values, or {@code null} if parsing fails
   */
  public static ParseContext parse(String[] args) {
    try {
      return parseArgs(args);
    } catch (IllegalArgumentException e) {
      Usage.showUsage(e.getMessage());
      return null; // To not pollute the console / usage text with exceptions
    }
  }

  /**
   * Internal method that performs the actual parsing of command-line arguments.
   *
   * @param args the arguments to parse
   * @return a {@link ParseContext} representing the parsed arguments
   * @throws IllegalArgumentException if an argument is unrecognized or a required value is missing
   */
  private static ParseContext parseArgs(String[] args) {
    ParseContext.Builder parseContextBuilder = ParseContext.builder();

    for (int i = 0; i < args.length; i++) {
      String arg = args[i];
      switch (arg) {
        case "-t", "--task":
          if (noValueProvided(args, i)) {
            throwMissingValueException(arg);
          }

          parseContextBuilder.withParseTask(args[++i]);
          break;
        case "-i", "--in-file":
          if (noValueProvided(args, i)) {
            throwMissingValueException(arg);
          }

          parseContextBuilder.withInputFile(args[++i]);
          break;
        case "-o", "--out-file":
          if (noValueProvided(args, i)) {
            throwMissingValueException(arg);
          }

          parseContextBuilder.withOutputFile(args[++i]);
          break;
        case "-d", "--out-dest":
          if (noValueProvided(args, i)) {
            throwMissingValueException(arg);
          }

          parseContextBuilder.withOutputTarget(args[++i]);
          break;
        case "-h", "--help":
          Usage.showUsage();
          break;
        default:
          throw new IllegalArgumentException("Unrecognised argument: " + arg);
      }
    }

    return parseContextBuilder.build();
  }

  /**
   * Checks if the current argument is missing its corresponding value.
   *
   * @param args         the array of command-line arguments
   * @param currentIndex the index of the current argument
   * @return {@code true} if the next argument is missing or starts with a dash
   * (indicating another option), {@code false} otherwise
   */
  private static boolean noValueProvided(String[] args, int currentIndex) {
    return currentIndex + 1 >= args.length || args[currentIndex + 1].startsWith("-");
  }

  /**
   * Throws an {@link IllegalArgumentException} indicating that the specified argument
   * requires a value.
   *
   * @param arg the argument that is missing its value
   * @throws IllegalArgumentException always
   */
  private static void throwMissingValueException(String arg) {
    throw new IllegalArgumentException(arg + " requires a value");
  }
}
