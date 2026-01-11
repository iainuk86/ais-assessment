package com.mcintosh.iain.cli;

/**
 * Utility class for displaying usage instructions in the console.
 */
public class Usage {

  private Usage() {
    throw new UnsupportedOperationException("Class not instantiable");
  }

  /**
   * The usage text template displayed to the user.
   */
  private static final String USAGE_TEXT = """
        %s%n
        Usage: java -jar <path to jar> [options]
        
        Options:
          -i, --in-file             Input text file. Required. Absolute path
          -o, --out-file            Output file. Optional. Prints to console if not provided
          -d, --out-dest            Output target. Optional. Accepted values: console (default), file
          -t, --task                Task to perform. Required. Accepted values: remove-vowels, count-consonants, count-slow-bike
          -h, --help                Show this help message
        """;

  /**
   * Prints the default usage message to the console.
   */
  public static void showUsage() {
    showUsage("AIS CLI Demonstration App by Iain McIntosh");
  }

  /**
   * Prints a custom usage message to the console.
   *
   * @param message the custom message to display at the top of the usage instructions
   */
  public static void showUsage(String message) {
    System.out.printf((USAGE_TEXT), message);
  }
}
