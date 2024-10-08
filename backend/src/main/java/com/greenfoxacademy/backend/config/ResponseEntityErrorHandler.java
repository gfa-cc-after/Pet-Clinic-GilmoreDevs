package com.greenfoxacademy.backend.config;

import com.greenfoxacademy.backend.errors.CannotSendEmailException;
import com.greenfoxacademy.backend.errors.CannotUpdateUserException;
import com.greenfoxacademy.backend.errors.CannotVerifyUserError;
import com.greenfoxacademy.backend.errors.UnableToDeleteProfileError;
import com.greenfoxacademy.backend.errors.UserAlreadyExistsError;
import java.util.HashMap;
import java.util.Map;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
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
  public ResponseEntity<HashMap<String, String>> handleUserAlreadyExistsError(
          UserAlreadyExistsError ex) {
    HashMap<String, String> errors = new HashMap<>();
    errors.put("error", "Email is already taken!");
    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);
  }

  /**
   * This method handles validation exceptions.
   *
   * @param ex the exception to be handled
   * @return a map with the field name and the error message
   */
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<Map<String, String>> handleValidationExceptions(
          MethodArgumentNotValidException ex) {
    Map<String, String> errors = new HashMap<>();
    ex.getBindingResult().getAllErrors().forEach((error) -> {
      String fieldName = ((FieldError) error).getField();
      String errorMessage = error.getDefaultMessage();
      errors.put(fieldName, errorMessage);
    });
    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);
  }

  /**
   * This method handles user cannot update exceptions.
   *
   * @param ex CannotUpdateUserException the exception to be handled
   * @return a map with the field name and the error message
   */
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  @ExceptionHandler(CannotUpdateUserException.class)
  public ResponseEntity<Map<String, String>> handleCannotUpdateUserExceptions(
          CannotUpdateUserException ex) {
    Map<String, String> errors = new HashMap<>();
    errors.put("error", ex.getMessage());
    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);
  }
  /**
   * Handle UnableToDeleteProfileError error globally in controllers.
   *
   * @param ex UnableToDeleteProfileError instance
   *
   * @return ResponseEntity with BAD_REQUEST and error key-value pair
   */

  @ResponseStatus(HttpStatus.BAD_REQUEST)
  @ExceptionHandler(UnableToDeleteProfileError.class)
  public ResponseEntity<?> handleUnableToDeleteProfileError(UnableToDeleteProfileError ex) {
    HashMap<String, String> errors = new HashMap<>();
    errors.put("error", "Unable to delete profile");
    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);
  }

  /**
   * Handle UnableToDeleteProfileError error globally in controllers.
   *
   * @param ex UnableToDeleteProfileError instance
   *
   * @return ResponseEntity with BAD_REQUEST and error key-value pair
   */
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  @ExceptionHandler(CannotSendEmailException.class)
  public ResponseEntity<?> handleCannotSendEmail(CannotSendEmailException ex) {
    HashMap<String, String> errors = new HashMap<>();
    errors.put("error", ex.getMessage());
    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);
  }

  /**
   * Handle UserNotVerifiedException error globally in controllers.
   *
   * @param ex UserNotVerifiedException instance
   *
   * @return ResponseEntity with BAD_REQUEST and error key-value pair
   */
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  @ExceptionHandler(CannotVerifyUserError.class)
  public ResponseEntity<?> handleVerifyUser(CannotVerifyUserError ex) {
    HashMap<String, String> errors = new HashMap<>();
    errors.put("error", ex.getMessage());
    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);
  }
}
