package com.greenfoxacademy.backend.controller;

import com.greenfoxacademy.backend.dtos.LoginUserDtoRecord;
import com.greenfoxacademy.backend.dtos.RegisterUserDto;
import com.greenfoxacademy.backend.services.UserService;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;


/**
 * REST controller where endpoints are handled.
 */
@RestController
@RequiredArgsConstructor
public class UserController {
  private final UserService userService;

  /**
   * This method registers a new user.
   *
   * @param registerUserDto the user to be registered
   * @return a response entity with the status code and the location of the new user
   */
  @CrossOrigin(origins = "http://localhost:5173")
  @PostMapping("/register")
  public ResponseEntity<?> registerUser(@Validated @RequestBody RegisterUserDto registerUserDto) {
    try {
      URI uri = URI.create("/users/" + userService.register(registerUserDto).id());
      return ResponseEntity.created(uri).build();
    } catch (Exception e) {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
    }
  }

  /**
   * This method handles validation exceptions.
   *
   * @param ex the exception to be handled
   * @return a map with the field name and the error message
   */
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  @ExceptionHandler(MethodArgumentNotValidException.class)
  public Map<String, String> handleValidationExceptions(MethodArgumentNotValidException ex) {
    Map<String, String> errors = new HashMap<>();
    ex.getBindingResult().getAllErrors().forEach((error) -> {
      String fieldName = ((FieldError) error).getField();
      String errorMessage = error.getDefaultMessage();
      errors.put(fieldName, errorMessage);
    });
    return errors;
  }

  /**
   * This method login an existing user.
   *
   * @param loginUserDtoRecord the user to be logged in
   * @return a response entity
   */
  @CrossOrigin(origins = "http://localhost:5173")
  @PostMapping("/login")
  public ResponseEntity<?> loginUser(@RequestBody LoginUserDtoRecord loginUserDtoRecord) {
    Map<String, String> response = new HashMap<>();
    response.put("Response", "Login OK");
    return ResponseEntity.ok().body(response);
  }

}

