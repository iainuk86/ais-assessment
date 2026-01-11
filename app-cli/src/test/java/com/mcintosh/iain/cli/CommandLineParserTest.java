package com.mcintosh.iain.cli;

import static org.assertj.core.api.Assertions.assertThat;

import com.mcintosh.iain.core.task.ParseContext;
import com.mcintosh.iain.core.task.enums.OutputTarget;
import com.mcintosh.iain.core.task.enums.ParseTaskType;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import org.junit.jupiter.api.Test;

class CommandLineParserTest {

  @Test
  void testParse_allValidValues_shortForm() {
    String[] args = new String[]{
        "-t", "remove-vowels",
        "-i", "input.txt",
        "-o", "output.txt",
        "-d", "file"
    };

    ParseContext context = CommandLineParser.parse(args);
    assertThat(context).isNotNull();
    assertThat(context.parseTaskType()).isEqualTo(ParseTaskType.REMOVE_VOWELS);
    assertThat(context.inputFile()).hasToString("input.txt");
    assertThat(context.outputFile()).hasToString("output.txt");
    assertThat(context.outputTarget()).isEqualTo(OutputTarget.FILE);
  }

  @Test
  void testParse_allValidValues_longForm() {
    String[] args = new String[]{
        "--task", "count-consonants",
        "--in-file", "input.txt",
        "--out-file", "output.txt",
        "--out-dest", "file"
    };

    ParseContext context = CommandLineParser.parse(args);
    assertThat(context).isNotNull();
    assertThat(context.parseTaskType()).isEqualTo(ParseTaskType.COUNT_CONSONANTS);
    assertThat(context.inputFile()).hasToString("input.txt");
    assertThat(context.outputFile()).hasToString("output.txt");
    assertThat(context.outputTarget()).isEqualTo(OutputTarget.FILE);
  }

  @Test
  void testParse_onlyRequiredValues() {
    String[] args = new String[]{
        "-t", "count-slow-bike",
        "-i", "input.txt"
    };

    ParseContext context = CommandLineParser.parse(args);
    assertThat(context).isNotNull();
    assertThat(context.parseTaskType()).isEqualTo(ParseTaskType.COUNT_SLOW_BIKE);
    assertThat(context.inputFile()).hasToString("input.txt");
    assertThat(context.outputFile()).isNull();
    assertThat(context.outputTarget()).isEqualTo(OutputTarget.CONSOLE);
  }

  // help is displayed tests

  @Test
  void testParse_inputFileIsRequired() {
    String[] args = new String[]{
        "-t", "remove-vowels",
        "-o", "output.txt",
        "-d", "file"
    };

    // Capture System.out to test the message displayed to the end user
    ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    PrintStream originalOut = System.out;
    System.setOut(new PrintStream(outContent));

    try {
      CommandLineParser.parse(args);

      // Verify what was printed
      String output = outContent.toString();
      assertThat(output).contains("Input file is required");
    } finally {
      // Restore original System.out
      System.setOut(originalOut);
    }
  }

  @Test
  void testParse_taskIsRequired() {
    String[] args = new String[]{
        "-i", "input.txt",
        "-o", "output.txt",
        "-d", "file"
    };

    // Capture System.out to test the message displayed to the end user
    ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    PrintStream originalOut = System.out;
    System.setOut(new PrintStream(outContent));

    try {
      CommandLineParser.parse(args);

      // Verify what was printed
      String output = outContent.toString();
      assertThat(output).contains("ParseTask is required");
    } finally {
      // Restore original System.out
      System.setOut(originalOut);
    }
  }

  @Test
  void testParse_argumentHasInvalidValue() {
    String[] args = new String[]{
        "-i", "input.txt",
        "-t", "remove-vowels",
        "-o", "-d",
        "-d", "file"
    };

    // Capture System.out to test the message displayed to the end user
    ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    PrintStream originalOut = System.out;
    System.setOut(new PrintStream(outContent));

    try {
      CommandLineParser.parse(args);

      // Verify what was printed
      String output = outContent.toString();
      assertThat(output).contains("-o requires a value");
    } finally {
      // Restore original System.out
      System.setOut(originalOut);
    }
  }

  @Test
  void testParse_helpIsDisplayedWithDefaultMessage() {
    String[] args = new String[]{
        "-i", "input.txt",
        "-t", "remove-vowels",
        "-o", "output.txt",
        "-d", "file",
        "-h"
    };

    // Capture System.out to test the message displayed to the end user
    ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    PrintStream originalOut = System.out;
    System.setOut(new PrintStream(outContent));

    try {
      CommandLineParser.parse(args);

      // Verify what was printed
      String output = outContent.toString();
      assertThat(output).contains("AIS CLI Demonstration App by Iain McIntosh");
    } finally {
      // Restore original System.out
      System.setOut(originalOut);
    }
  }
}
