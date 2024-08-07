package com.greenfoxacademy.backend.errors;

public class CannotUpdateUserException extends Exception {
  public CannotUpdateUserException(String message) {
    super(message);
  }
}
