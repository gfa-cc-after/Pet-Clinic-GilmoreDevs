package com.greenfoxacademy.backend.errors;

/**
 * For handing user already exist error.
 */
public class UserAlreadyExistsError extends RuntimeException {
  public UserAlreadyExistsError(String emailIsAlreadyTaken) {
  }
}
