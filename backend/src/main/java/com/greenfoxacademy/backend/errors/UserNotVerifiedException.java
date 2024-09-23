package com.greenfoxacademy.backend.errors;

/**
 * Error to throw when a user cannot be verified.
 * <p>
 * This error should be thrown when a user cannot be verified.
 * </p>
 */
public class UserNotVerifiedException extends RuntimeException {
  public UserNotVerifiedException(String message) {
    super(message);
  }
}