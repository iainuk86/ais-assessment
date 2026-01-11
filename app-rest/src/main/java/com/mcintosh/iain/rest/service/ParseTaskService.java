package com.mcintosh.iain.rest.service;

import com.mcintosh.iain.core.CoreProcessor;
import com.mcintosh.iain.core.task.ParseContext;
import com.mcintosh.iain.rest.model.ParseTaskDto;
import java.io.IOException;
import org.springframework.stereotype.Service;

/**
 * Main service responsible for initializing and executing parse tasks.
 * <p>
 * This service converts a {@link ParseTaskDto} received from the UI into a {@link ParseContext}
 * and then delegates the processing to the {@link CoreProcessor}.
 * Any I/O exceptions during processing are wrapped in an exception to be handled by the global
 * exception handler. This will convert the exception to UI-friendly DTO fields.
 * </p>
 */
@Service
public class ParseTaskService {

  /**
   * Initializes and executes a parse task based on the provided DTO.
   *
   * @param parseTaskDto the DTO containing the parse task information
   * @throws IllegalStateException if an I/O error occurs during processing
   */
  public void initParseTask(ParseTaskDto parseTaskDto) {
    ParseContext parseContext = ParseContext.builder()
        .withInputFile(parseTaskDto.getInputFile())
        .withOutputFile(parseTaskDto.getOutputFile())
        .withOutputTarget(parseTaskDto.getOutputTarget())
        .withParseTask(parseTaskDto.getParseTask())
        .build();

    try {
      CoreProcessor.process(parseContext);
    } catch (IOException e) {
      // For demo purposes I just throw an exception to be handled by the global exception handler
      throw new IllegalStateException(e.getMessage());
    }
  }
}
