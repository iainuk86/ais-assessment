package com.mcintosh.iain.core;

import com.mcintosh.iain.core.task.ParseContext;
import com.mcintosh.iain.core.task.strategy.ParseTask;
import com.mcintosh.iain.core.task.strategy.ParseTaskRegistry;
import com.mcintosh.iain.core.task.enums.OutputTarget;
import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;

/**
 * Main processing entry point for the library.
 * <p>
 * Use the {@link #process(ParseContext)}
 * method to execute a parse task on an input file and write the output to the specified
 * target (console or file).
 * <p>
 * Example usage:
 * <pre>{@code
 * ParseContext context = ParseContext.builder()
 *     .withParseTask("remove-vowels")
 *     .withInputFile("/path/to/input.txt")
 *     .withOutputTarget("console")
 *     .build();
 *
 * CoreProcessor.process(context);
 * }</pre>
 */
public final class CoreProcessor {

  private CoreProcessor() {
    throw new UnsupportedOperationException("Class not instantiable");
  }

  /**
   * Processes the input file according to the {@link ParseContext}.
   *
   * @param parseContext the context containing input file, output target, and parse task information
   * @throws IOException if reading or writing the file fails
   */
  public static void process(ParseContext parseContext) throws IOException {
    // Determine strategy to use to process input
    ParseTask strategy = ParseTaskRegistry.getStrategy(parseContext.parseTaskType());

    // Read the input
    String input = readFileContents(parseContext.inputFile());

    // Process into required output. Add a newline character in case of appending to existing file
    String output = strategy.execute(input) + "\n";

    // Write to destination
    writeOutput(output, parseContext);
  }

  /**
   * Reads the entire contents of a file into a String.
   *
   * @param filePath the file to read
   * @return the contents of the file as a String, with line breaks preserved
   * @throws IOException if reading the file fails
   */
  private static String readFileContents(Path filePath) throws IOException {
    StringBuilder content = new StringBuilder();

    try (BufferedReader reader = Files.newBufferedReader(filePath)) {
      String line;

      while ((line = reader.readLine()) != null) {
        content.append(line).append("\n");
      }
    }

    return content.toString();
  }

  /**
   * Writes the processed output to the destination specified in the context.
   *
   * @param output the processed content
   * @param parseContext the context specifying the output target
   * @throws IOException if writing the output fails
   */
  private static void writeOutput(String output, ParseContext parseContext) throws IOException {
    if (OutputTarget.CONSOLE == parseContext.outputTarget()) {
      writeToConsole(output);
    } else {
      writeToFile(output, parseContext.outputFile());
    }
  }

  /**
   * Prints output to the console.
   *
   * @param output the content to print
   */
  private static void writeToConsole(String output) {
    System.out.println(output);
  }

  /**
   * Writes output to a file, creating or appending to it as needed.
   *
   * @param output the content to write
   * @param filePath the file to write to
   * @throws IOException if writing the file fails
   */
  private static void writeToFile(String output, Path filePath) throws IOException {
    try {
      Files.writeString(filePath, output, StandardOpenOption.CREATE, StandardOpenOption.APPEND);
    } catch (IOException e) {
      throw new IOException("Failed to write output file", e);
    }
  }
}
