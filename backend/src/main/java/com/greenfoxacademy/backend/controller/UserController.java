package com.greenfoxacademy.backend.controller;

import com.greenfoxacademy.backend.dtos.*;
import com.greenfoxacademy.backend.errors.UserAlreadyExistsError;
import com.greenfoxacademy.backend.services.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

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
  @PostMapping("/register")
  public ResponseEntity<RegisterResponseDto> registerUser(@Validated @RequestBody RegisterRequestDto registerRequestDto) throws UserAlreadyExistsError {
    return ResponseEntity.status(HttpStatus.OK).body(userService.register(registerRequestDto));
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
  @PostMapping("/login")
  public ResponseEntity<LoginResponseDto> loginUser(@RequestBody LoginRequestDto loginRequestDto) {
    try {
      return ResponseEntity.status(HttpStatus.OK).body(userService.login(loginRequestDto));
    } catch (Exception e) {
      return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }
  }

  /**
   * This method logs in a user.
   * Outcomes:
   * - If the user is not found, return a 401 status code.
   * - If the user is found, return a 200 status code and the token.
   *
   * @param profileUpdateRequestDto the user to be logged in
   * @return a response entity with the status code and the token
   */
  @PatchMapping("/profile-update")
  public ResponseEntity<ProfileUpdateResponseDto> userProfileUpdate(Principal principal, @RequestBody ProfileUpdateRequestDto profileUpdateRequestDto) {
    try {
      return ResponseEntity.status(HttpStatus.OK).body(userService.profileUpdate(principal.getName(), profileUpdateRequestDto));
    } catch (Exception e) {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }
  }
}