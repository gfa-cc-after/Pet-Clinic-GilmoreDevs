package com.greenfoxacademy.backend.errors;

/**
 * Error to throw when a user cannot be verified.
 * <p>
 * This error should be thrown when a user cannot be verified.
 * </p>
 */
public class CannotVerifyUserError extends RuntimeException {
  public CannotVerifyUserError(String message) {
    super(message);
  }
}
