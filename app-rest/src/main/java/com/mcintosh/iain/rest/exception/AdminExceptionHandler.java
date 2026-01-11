package com.mcintosh.iain.rest.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * Global exception handler for the REST controllers.
 */
@RestControllerAdvice
public class AdminExceptionHandler {
  private static final Logger log = LoggerFactory.getLogger(AdminExceptionHandler.class);

  @ResponseStatus(HttpStatus.BAD_REQUEST)
  @ExceptionHandler(IllegalArgumentException.class)
  public ApiErrorDto handleIllegalArgumentException(IllegalArgumentException ex) {
    log.error("ApiException occurred: {}", ex.getMessage());

    return ApiErrorDto.fromException(ex);
  }
}
