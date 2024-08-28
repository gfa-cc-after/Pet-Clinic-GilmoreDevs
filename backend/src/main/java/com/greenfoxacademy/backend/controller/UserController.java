package com.greenfoxacademy.backend.controller;

import com.greenfoxacademy.backend.dtos.LoginRequestDto;
import com.greenfoxacademy.backend.dtos.LoginResponseDto;
import com.greenfoxacademy.backend.dtos.ProfileUpdateRequestDto;
import com.greenfoxacademy.backend.dtos.ProfileUpdateResponseDto;
import com.greenfoxacademy.backend.dtos.RegisterRequestDto;
import com.greenfoxacademy.backend.dtos.RegisterResponseDto;
import com.greenfoxacademy.backend.errors.CannotUpdateUserException;
import com.greenfoxacademy.backend.errors.UnableToDeleteProfileError;
import com.greenfoxacademy.backend.errors.UserAlreadyExistsError;
import com.greenfoxacademy.backend.services.user.UserService;

import java.security.Principal;
import java.util.UUID;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
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
  @PostMapping("/register")
  public ResponseEntity<RegisterResponseDto> registerUser(
          @Validated @RequestBody RegisterRequestDto registerRequestDto
  )throws UserAlreadyExistsError {
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
   * Outcomes:
   * - If the user is not found, return a 401 status code.
   * - If the user is found, return a 200 status code and the token.
   *
   * @param profileUpdateRequestDto the user to be logged in
   * @return a response entity with the status code and the token
   */
  @PatchMapping("/profile-update")
  public ResponseEntity<ProfileUpdateResponseDto> userProfileUpdate(
          Principal principal,
          @RequestBody ProfileUpdateRequestDto profileUpdateRequestDto
  ) throws CannotUpdateUserException {
    return ResponseEntity
            .status(HttpStatus.OK)
            .body(userService.profileUpdate(principal.getName(), profileUpdateRequestDto));
  }

  /**
   * This method delete a user from the database.
   *
   * @param principal the user that is authenticated
   * @return a response entity with the status code
   */
  @DeleteMapping("/delete-profile")
  public ResponseEntity<?> deleteProfile(Principal principal) throws UnableToDeleteProfileError {
    userService.deleteProfile(principal.getName());
    return ResponseEntity.status(HttpStatus.ACCEPTED).build();
  }

  // http://localhost:8080/verification?code=56565-55656-56-56-5-65-6-56
  @GetMapping("/verification")
  public ResponseEntity<?> verificationPage(@RequestParam(value = "code") UUID verificationCode) {
    userService.verifyUser(verificationCode);
    return ResponseEntity.status(HttpStatus.OK).build();
  }
}