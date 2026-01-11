package com.mcintosh.iain.rest.exception;

import java.io.Serial;
import java.io.Serializable;

/**
 * DTO representing an API error response.
 * <p>
 * This DTO is returned to the client when an exception occurs while processing the parsing task.
 * It contains a simple error message describing the problem.
 * This class could easily be further extended with extra information of interest to the end user.
 * </p>
 */
public class ApiErrorDto implements Serializable {

  @Serial
  private static final long serialVersionUID = 1L;

  private String message;

  public ApiErrorDto() {
  }

  public ApiErrorDto(String message) {
    this.message = message;
  }

  public static ApiErrorDto fromException(RuntimeException e) {
    return new ApiErrorDto(e.getMessage());
  }

  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }
}
