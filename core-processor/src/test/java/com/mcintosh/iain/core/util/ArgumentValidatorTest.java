package com.mcintosh.iain.core.util;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

import com.mcintosh.iain.core.task.enums.OutputTarget;
import com.mcintosh.iain.core.task.enums.ParseTaskType;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

class ArgumentValidatorTest {

  @TempDir
  Path tempDir;

  @Test
  void validateInputFile_validReadableFile() throws Exception {
    Path inputFile = Files.createFile(tempDir.resolve("input.txt"));

    Path result = ArgumentValidator.validateInputFile(inputFile.toString());

    assertThat(result)
        .isEqualTo(inputFile)
        .exists()
        .isRegularFile()
        .isReadable();
  }

  @Test
  void validateInputFile_fileDoesNotExistThrowsException() {
    Path missingFile = tempDir.resolve("missing.txt");

    assertThatThrownBy(() ->
        ArgumentValidator.validateInputFile(missingFile.toString())
    )
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessage("Invalid input file");
  }

  @Test
  void validateInputFile_isDirectoryThrowsException() {
    assertThatThrownBy(() ->
        ArgumentValidator.validateInputFile(tempDir.toString())
    )
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessage("Invalid input file");
  }

  @Test
  void validateInputFile_trimsAndRemovesQuotes() throws Exception {
    Path inputFile = Files.createFile(tempDir.resolve("quoted.txt"));
    String arg = "  \"" + inputFile + "\"  ";

    Path result = ArgumentValidator.validateInputFile(arg);

    assertThat(result).isEqualTo(inputFile);
  }

  @Test
  void validateOrCreateOutputFile_existingWritableFile() throws Exception {
    Path outputFile = Files.createFile(tempDir.resolve("output.txt"));

    Path result = ArgumentValidator.validateOrCreateOutputFile(outputFile.toString());

    assertThat(result)
        .isEqualTo(outputFile)
        .exists()
        .isWritable();
  }

  @Test
  void validateOrCreateOutputFile_createsMissingFileAndParentDirs() {
    Path outputFile = tempDir.resolve("nested/dir/output.txt");

    Path result = ArgumentValidator.validateOrCreateOutputFile(outputFile.toString());

    assertThat(result)
        .isEqualTo(outputFile)
        .exists()
        .isWritable();

    assertThat(outputFile.getParent()).exists();
  }

  @Test
  void validateOrCreateOutputFile_trimsAndRemovesQuotes() {
    Path outputFile = tempDir.resolve("quotedOutput.txt");
    String arg = " '" + outputFile + "' ";

    Path result = ArgumentValidator.validateOrCreateOutputFile(arg);

    assertThat(result)
        .isEqualTo(outputFile)
        .exists()
        .isWritable();
  }

  @Test
  void validateOutputTarget_validValue() {
    Arrays.stream(OutputTarget.values()).forEach(format -> {
      OutputTarget asEnum = ArgumentValidator.validateOutputTarget(format.name());
      assertThat(asEnum).isEqualTo(format);
    });
  }

  @Test
  void validateOutputTarget_invalidValue() {
    assertThatThrownBy(() ->
        ArgumentValidator.validateOutputTarget("invalid")
    )
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessage("Invalid output target");
  }

  @Test
  void validateOutputTarget_trimsAndRemovesQuotes() {
    OutputTarget format = ArgumentValidator.validateOutputTarget(" \"file\" ");

    assertThat(format).isEqualTo(OutputTarget.FILE);
  }

  @Test
  void validateParseTask_validValue() {
    ParseTaskType task = ArgumentValidator.validateParseTask("count-consonants");

    assertThat(task).isEqualTo(ParseTaskType.COUNT_CONSONANTS);
  }

  @Test
  void validateParseTask_invalidValue_throwsException() {
    assertThatThrownBy(() ->
        ArgumentValidator.validateParseTask("invalid")
    )
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessage("Invalid parse task");
  }

  @Test
  void validateParseTask_trimsAndRemovesQuotes() {
    ParseTaskType task = ArgumentValidator.validateParseTask(" 'remove-vowels' ");

    assertThat(task).isEqualTo(ParseTaskType.REMOVE_VOWELS);
  }
}
