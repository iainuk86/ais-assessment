package com.mcintosh.iain.rest.controller;

import static org.hamcrest.core.Is.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

@SpringBootTest
@AutoConfigureMockMvc
class ParseTaskControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @Test
  void testValidInputForm_outputTargetConsole() throws Exception {
    MultiValueMap<String, String> formData = new LinkedMultiValueMap<>();
    formData.add("inputFile", "input.txt");
    formData.add("parseTask", "remove-vowels");
    formData.add("outputTarget", "console");

    mockMvc.perform(post("/parse")
            .params(formData)
            .contentType(MediaType.APPLICATION_FORM_URLENCODED))
        .andExpect(status().isOk());
  }

  @Test
  void testValidInputForm_outputTargetFile() throws Exception {
    MultiValueMap<String, String> formData = new LinkedMultiValueMap<>();
    formData.add("inputFile", "input.txt");
    formData.add("parseTask", "remove-vowels");
    formData.add("outputTarget", "file");
    formData.add("outputFile", "output.txt");

    mockMvc.perform(post("/parse")
            .params(formData)
            .contentType(MediaType.APPLICATION_FORM_URLENCODED))
        .andExpect(status().isOk());
  }

  @Test
  void testInputFileIsRequired() throws Exception {
    MultiValueMap<String, String> formData = new LinkedMultiValueMap<>();
    formData.add("parseTask", "remove-vowels");
    formData.add("outputTarget", "console");

    mockMvc.perform(post("/parse")
            .params(formData)
            .contentType(MediaType.APPLICATION_FORM_URLENCODED))
        .andExpect(status().isBadRequest())
        .andExpect(jsonPath("$.message", is("Input file is required")));
  }

  @Test
  void testParseTaskIsRequired() throws Exception {
    MultiValueMap<String, String> formData = new LinkedMultiValueMap<>();
    formData.add("inputFile", "input.txt");
    formData.add("outputTarget", "console");

    mockMvc.perform(post("/parse")
            .params(formData)
            .contentType(MediaType.APPLICATION_FORM_URLENCODED))
        .andExpect(status().isBadRequest())
        .andExpect(jsonPath("$.message", is("ParseTask is required")));
  }

  @Test
  void testErrorDtoContainsErrorMessage_inputFile() throws Exception {
    MultiValueMap<String, String> formData = new LinkedMultiValueMap<>();
    formData.add("inputFile", "file-does-not-exist.txt");
    formData.add("parseTask", "remove-vowels");
    formData.add("outputTarget", "file");
    formData.add("outputFile", "output.txt");

    mockMvc.perform(post("/parse")
            .params(formData)
            .contentType(MediaType.APPLICATION_FORM_URLENCODED))
        .andExpect(status().isBadRequest())
        .andExpect(jsonPath("$.message", is("Invalid input file")));
  }

  @Test
  void testErrorDtoContainsErrorMessage_parseTask() throws Exception {
    MultiValueMap<String, String> formData = new LinkedMultiValueMap<>();
    formData.add("inputFile", "input.txt");
    formData.add("parseTask", "invalid");
    formData.add("outputTarget", "file");
    formData.add("outputFile", "output.txt");

    mockMvc.perform(post("/parse")
            .params(formData)
            .contentType(MediaType.APPLICATION_FORM_URLENCODED))
        .andExpect(status().isBadRequest())
        .andExpect(jsonPath("$.message", is("Invalid parse task")));
  }

  @Test
  void testErrorDtoContainsErrorMessage_outputTarget() throws Exception {
    MultiValueMap<String, String> formData = new LinkedMultiValueMap<>();
    formData.add("inputFile", "input.txt");
    formData.add("parseTask", "remove-vowels");
    formData.add("outputTarget", "invalid");
    formData.add("outputFile", "output.txt");

    mockMvc.perform(post("/parse")
            .params(formData)
            .contentType(MediaType.APPLICATION_FORM_URLENCODED))
        .andExpect(status().isBadRequest())
        .andExpect(jsonPath("$.message", is("Invalid output target")));
  }
}
