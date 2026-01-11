package com.mcintosh.iain.rest.model;

import jakarta.validation.constraints.NotBlank;

/**
 * DTO representing a parse task submitted from the UI.
 * <p>
 * This DTO is used to capture the input data from the web form, including the input file, the
 * type of parse task, and the output destination etc.
 * </p>
 */
public class ParseTaskDto {

  @NotBlank
  private String inputFile;
  @NotBlank
  private String parseTask;
  private String outputFile;
  private String outputTarget;

  public @NotBlank String getInputFile() {
    return inputFile;
  }

  public void setInputFile(@NotBlank String inputFile) {
    this.inputFile = inputFile;
  }

  public @NotBlank String getParseTask() {
    return parseTask;
  }

  public void setParseTask(@NotBlank String parseTask) {
    this.parseTask = parseTask;
  }

  public String getOutputFile() {
    return outputFile;
  }

  public void setOutputFile(String outputFile) {
    this.outputFile = outputFile;
  }

  public String getOutputTarget() {
    return outputTarget;
  }

  public void setOutputTarget(String outputTarget) {
    this.outputTarget = outputTarget;
  }
}
