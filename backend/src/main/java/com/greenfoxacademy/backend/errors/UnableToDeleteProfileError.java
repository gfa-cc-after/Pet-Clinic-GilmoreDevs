package com.greenfoxacademy.backend.errors;

/**
 * This class represents an error that occurs when a profile cannot be deleted.
 * It extends the RuntimeException class.
 * The class has two constructors, one with a message and one without.
 * The message is passed to the super class.
 */
public class UnableToDeleteProfileError extends RuntimeException {
  public UnableToDeleteProfileError(String message) {
    super(message);
  }
  public UnableToDeleteProfileError() {
    super();
  }
}
