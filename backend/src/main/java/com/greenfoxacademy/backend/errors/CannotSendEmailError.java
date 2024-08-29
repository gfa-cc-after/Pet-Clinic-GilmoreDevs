package com.greenfoxacademy.backend.errors;

/**
 * Error message represents the error when not able to send email.
 */
public class CannotSendEmailError extends Exception {
  public CannotSendEmailError(String message) {
    super(message);
  }
}
