package com.greenfoxacademy.backend.config;

import com.greenfoxacademy.backend.errors.UserAlreadyExistsError;
import java.util.HashMap;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * This class handles Email error.
 */
@ControllerAdvice
public class ResponseEntityErrorHandler {
  /**
   * This method handles validation exceptions.
   *
   * @param ex the exception to be handled
   * @return a map with the field name and the error message
   */
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  @ExceptionHandler(UserAlreadyExistsError.class)
  public ResponseEntity<HashMap<String, String>>
        handleUserAlreadyExistsError(UserAlreadyExistsError ex) {
    HashMap<String, String> errors = new HashMap<>();
    errors.put("error", "Email is already taken!");
    return ResponseEntity.badRequest().body(errors);
  }
}