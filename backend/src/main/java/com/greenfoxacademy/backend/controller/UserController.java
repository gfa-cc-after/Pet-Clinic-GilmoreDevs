package com.greenfoxacademy.backend.controller;

import com.greenfoxacademy.backend.dtos.*;
import com.greenfoxacademy.backend.errors.CannotUpdateUserException;
import com.greenfoxacademy.backend.errors.UserAlreadyExistsError;
import com.greenfoxacademy.backend.models.User;
import com.greenfoxacademy.backend.services.UserService;
import io.swagger.v3.oas.annotations.OpenAPI31;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;


/**
 * REST controller where endpoints are handled.
 */
@RestController
@RequestMapping("/api/user/")
@Tag(name = "User Controller")
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
  @PostMapping("register")
  @Operation(summary = "This method creates a user.")
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
  @Operation(summary = "This method generates a jwt token for a user based on credentials.")
  @CrossOrigin(origins = "http://localhost:5173")
  @PostMapping("login")
  public ResponseEntity<LoginResponseDto> loginUser(@Valid @RequestBody LoginRequestDto loginRequestDto) {
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
  @CrossOrigin(origins = "http://localhost:5173")
  @Operation(summary = "This method updates the authenticated user.")
  @PatchMapping("profile-update")
  public ResponseEntity<ProfileUpdateResponseDto> userProfileUpdate(@AuthenticationPrincipal User user, @Valid @RequestBody ProfileUpdateRequestDto profileUpdateRequestDto) throws CannotUpdateUserException {
    return ResponseEntity.status(HttpStatus.OK).body(userService.profileUpdate(user, profileUpdateRequestDto));
  }
}