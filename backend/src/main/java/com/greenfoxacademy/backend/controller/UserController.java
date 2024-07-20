package com.greenfoxacademy.backend.controller;

import com.greenfoxacademy.backend.dtos.LoginRequestDto;
import com.greenfoxacademy.backend.dtos.LoginResponseDto;
import com.greenfoxacademy.backend.dtos.RegisterRequestDto;
import com.greenfoxacademy.backend.errors.UserAlreadyExistsError;
import com.greenfoxacademy.backend.services.UserService;

import java.net.URI;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
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
   * @param registerRequestDto the user to be registered
   * @return a response entity with the status code and the location of the new user
   */
  @CrossOrigin(origins = "http://localhost:5173")
  @PostMapping("/register")
  public ResponseEntity<?> registerUser(@Validated @RequestBody RegisterRequestDto registerRequestDto)
          throws UserAlreadyExistsError {
    URI uri = URI.create("/users/" + userService.register(registerRequestDto).id());
    return ResponseEntity.status(HttpStatus.CREATED).body(uri);
  }

  /**
   * This method logs in a user.
   * Outcomes:
   * - If the user is not found, return a 401 status code.
   * - If the user is found, return a 200 status code and the token.
   *
   * @param loginRequestDto the user to be logged in
   * @return a response entity with the status code and the token
   */
  //TODO: add validation for the LoginRequestDto after that re-add the @Validated annotation
  @CrossOrigin(origins = "http://localhost:5173")
  @PostMapping("/login")
  public ResponseEntity<LoginResponseDto> loginUser(
          @RequestBody LoginRequestDto loginRequestDto
  ) {
    try {
      return ResponseEntity.status(HttpStatus.OK).body(userService.login(loginRequestDto));
    } catch (Exception e) {
      return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }
  }
}