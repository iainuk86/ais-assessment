package com.mcintosh.iain.core.task;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.mcintosh.iain.core.task.enums.OutputTarget;
import com.mcintosh.iain.core.task.enums.ParseTaskType;
import java.nio.file.Files;
import java.nio.file.Path;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

class ParseContextTest {

  @TempDir
  Path tempDir;

  @Test
  void buildWithAllArguments() throws Exception {
    Path inputFile = Files.createFile(tempDir.resolve("input.txt"));
    Path outputFile = tempDir.resolve("output.json");

    ParseContext context = ParseContext.builder()
        .withParseTask("count-slow-bike")
        .withInputFile(inputFile.toString())
        .withOutputFile(outputFile.toString())
        .withOutputTarget("file")
        .build();

    assertThat(context.parseTaskType())
        .isEqualTo(ParseTaskType.COUNT_SLOW_BIKE);

    assertThat(context.inputFile())
        .isEqualTo(inputFile);

    assertThat(context.outputFile())
        .exists()
        .isEqualTo(outputFile);

    assertThat(context.outputTarget())
        .isEqualTo(OutputTarget.FILE);
  }

  @Test
  void buildWithoutOutputFile_defaultsToConsoleOutput() throws Exception {
    Path inputFile = Files.createFile(tempDir.resolve("input.txt"));

    ParseContext context = ParseContext.builder()
        .withParseTask("count-slow-bike")
        .withInputFile(inputFile.toString())
        .build();

    assertThat(context.outputFile()).isNull();
    assertThat(context.outputTarget()).isEqualTo(OutputTarget.CONSOLE);
  }

  @Test
  void buildWithOutputFileButNoFormat_defaultsToConsole() throws Exception {
    Path inputFile = Files.createFile(tempDir.resolve("input.txt"));
    Path outputFile = tempDir.resolve("output.txt");

    ParseContext context = ParseContext.builder()
        .withParseTask("count-slow-bike")
        .withInputFile(inputFile.toString())
        .withOutputFile(outputFile.toString())
        .build();

    assertThat(context.outputFile())
        .isEqualTo(outputFile)
        .exists();

    assertThat(context.outputTarget())
        .isEqualTo(OutputTarget.CONSOLE);
  }

  @Test
  void buildMissingParseTaskThrowsException() {
    assertThatThrownBy(() ->
        ParseContext.builder()
            .withInputFile("input.txt")
            .build()
    )
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessage("ParseTask is required");
  }

  @Test
  void buildBlankParseTaskThrowsException() {
    assertThatThrownBy(() ->
        ParseContext.builder()
            .withParseTask("   ")
            .withInputFile("input.txt")
            .build()
    )
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessage("ParseTask is required");
  }

  @Test
  void buildMissingInputFileThrowsException() {
    assertThatThrownBy(() ->
        ParseContext.builder()
            .withParseTask("count-slow-bike")
            .build()
    )
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessage("Input file is required");
  }

  @Test
  void buildBlankInputFileThrowsException() {
    assertThatThrownBy(() ->
        ParseContext.builder()
            .withParseTask("count-slow-bike")
            .withInputFile("   ")
            .build()
    )
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessage("Input file is required");
  }

  @Test
  void buildInvalidParseTaskPropagatesValidatorException() throws Exception {
    Path inputFile = Files.createFile(tempDir.resolve("input.txt"));

    assertThatThrownBy(() ->
        ParseContext.builder()
            .withParseTask("invalid")
            .withInputFile(inputFile.toString())
            .build()
    )
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessage("Invalid parse task");
  }

  @Test
  void buildInvalidInputFilePropagatesValidatorException() {
    assertThatThrownBy(() ->
        ParseContext.builder()
            .withParseTask("count-slow-bike")
            .withInputFile("does-not-exist.txt")
            .build()
    )
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessage("Invalid input file");
  }

  @Test
  void buildInvalidOutputTargetPropagatesValidatorException() throws Exception {
    Path inputFile = Files.createFile(tempDir.resolve("input.txt"));
    Path outputFile = tempDir.resolve("output.txt");

    assertThatThrownBy(() ->
        ParseContext.builder()
            .withParseTask("count-slow-bike")
            .withInputFile(inputFile.toString())
            .withOutputFile(outputFile.toString())
            .withOutputTarget("invalid")
            .build()
    )
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessage("Invalid output target");
  }

  @Test
  void buildTrimsAndRemovesQuotesFromArguments() throws Exception {
    Path inputFile = Files.createFile(tempDir.resolve("input.txt"));
    Path outputFile = tempDir.resolve("output.json");

    ParseContext context = ParseContext.builder()
        .withParseTask(" \"count-slow-bike\" ")
        .withInputFile(" '" + inputFile + "' ")
        .withOutputFile(" \"" + outputFile + "\" ")
        .withOutputTarget(" 'file' ")
        .build();

    assertThat(context.parseTaskType()).isEqualTo(ParseTaskType.COUNT_SLOW_BIKE);
    assertThat(context.inputFile()).isEqualTo(inputFile);
    assertThat(context.outputFile()).isEqualTo(outputFile);
    assertThat(context.outputTarget()).isEqualTo(OutputTarget.FILE);
  }
}
