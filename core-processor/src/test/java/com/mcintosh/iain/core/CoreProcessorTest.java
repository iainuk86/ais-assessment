package com.mcintosh.iain.core;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.mcintosh.iain.core.task.ParseContext;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.nio.file.Files;
import java.nio.file.Path;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

class CoreProcessorTest {

  @TempDir
  Path tempDir;

  @Test
  void process_withConsoleOutput_writesToConsole() throws Exception {
    Path inputFile = Files.createFile(tempDir.resolve("input.txt"));
    Files.writeString(inputFile, "Hello slow bike");

    ParseContext context = ParseContext.builder()
        .withParseTask("count-slow-bike")
        .withInputFile(inputFile.toString())
        .build();

    // Capture console
    ByteArrayOutputStream outStream = new ByteArrayOutputStream();
    PrintStream originalOut = System.out;
    System.setOut(new PrintStream(outStream));

    try {
      CoreProcessor.process(context);
    } finally {
      System.setOut(originalOut);
    }

    assertThat(outStream.toString().trim()).isEqualTo("1");
  }

  @Test
  void process_withFileOutput_writesToFile() throws Exception {
    Path inputFile = Files.createFile(tempDir.resolve("input.txt"));
    Files.writeString(inputFile, "banana");

    Path outputFile = tempDir.resolve("output.txt");

    ParseContext context = ParseContext.builder()
        .withParseTask("remove-vowels")
        .withInputFile(inputFile.toString())
        .withOutputFile(outputFile.toString())
        .withOutputTarget("file")
        .build();

    CoreProcessor.process(context);

    String output = Files.readString(outputFile);
    assertThat(output).isEqualTo("bnn\n\n");
  }

  @Test
  void process_writeFileFails_throwsException() throws Exception {
    Path inputFile = Files.createFile(tempDir.resolve("input.txt"));
    Files.writeString(inputFile, "banana");

    // Create a directory where the file would be
    Path outputFile = tempDir.resolve("outputDir");
    Files.createDirectory(outputFile);

    ParseContext context = ParseContext.builder()
        .withParseTask("count-consonants")
        .withInputFile(inputFile.toString())
        .withOutputFile(outputFile.toString())
        .withOutputTarget("file")
        .build();

    assertThatThrownBy(() -> CoreProcessor.process(context))
        .isInstanceOf(IOException.class)
        .hasMessageContaining("Failed to write output file");
  }

}
