package com.greenfoxacademy.backend.errors;

public class UserAlreadyExistsError extends RuntimeException {
  public UserAlreadyExistsError(String emailIsAlreadyTaken) {
  }
}
