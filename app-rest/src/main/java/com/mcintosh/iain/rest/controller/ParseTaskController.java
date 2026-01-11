package com.mcintosh.iain.rest.controller;

import com.mcintosh.iain.rest.model.ParseTaskDto;
import com.mcintosh.iain.rest.service.ParseTaskService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller responsible for handling parsing tasks submitted via the web form.
 */
@RestController
@RequestMapping("/parse")
public class ParseTaskController {

  private final ParseTaskService parseTaskService;

  @Autowired
  public ParseTaskController(ParseTaskService parseTaskService) {
    this.parseTaskService = parseTaskService;
  }

  /**
   * Handles the submission of a parse task form.
   * <p>
   * Accepts a {@link ParseTaskDto} submitted via a form and validates it.
   * </p>
   *
   * @param parseTaskDto the DTO containing the parse task information
   * @return an HTTP 200 OK response if the task is successfully completed
   */
  @PostMapping
  public ResponseEntity<Void> initParseTask(@Valid @ModelAttribute ParseTaskDto parseTaskDto) {
    parseTaskService.initParseTask(parseTaskDto);

    return ResponseEntity.ok().build();
  }
}
