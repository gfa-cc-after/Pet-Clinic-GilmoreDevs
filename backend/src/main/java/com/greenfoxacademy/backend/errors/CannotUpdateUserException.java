package com.greenfoxacademy.backend.errors;

/**
 * This class handles user cannot update exceptions.
 */
public class CannotUpdateUserException extends Exception {
  public CannotUpdateUserException(String s) {
    super(s);
  }
}
