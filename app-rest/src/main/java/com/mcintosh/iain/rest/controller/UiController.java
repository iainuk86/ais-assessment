package com.mcintosh.iain.rest.controller;

import com.mcintosh.iain.core.task.enums.ParseTaskType;
import com.mcintosh.iain.rest.model.ParseTaskDto;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Controller responsible for serving the web UI pages.
 */
@Controller
public class UiController {

  /**
   * Displays the main parse task form, populating the model for Thymeleaf to use.
   */
  @GetMapping({"/", "/index"})
  public String showForm(Model model) {
    model.addAttribute("parseTaskDto", new ParseTaskDto());
    model.addAttribute("parseTaskTypes", ParseTaskType.values());

    return "index";
  }
}
