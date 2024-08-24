package com.greenfoxacademy.backend.errors;

/**
 * Exception thrown when a user cannot be updated.
 */
public class CannotUpdateUserException extends Exception {
  public CannotUpdateUserException(String message) {
    super(message);
  }
}
