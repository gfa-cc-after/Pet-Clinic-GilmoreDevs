package com.greenfoxacademy.backend.services;


import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.ArrayList;


public class PasswordConstraintValidator implements ConstraintValidator<ValidPassword, String> {
  
  @Override
  public boolean isValid(String password, ConstraintValidatorContext constraintValidatorContext) {
    if (password == null) {
      constraintValidatorContext.disableDefaultConstraintViolation();
      constraintValidatorContext.buildConstraintViolationWithTemplate("Password should be added").addConstraintViolation();
      return false;
    }
    boolean isValid = true;
    ArrayList<String> errorMessages = new ArrayList<>();
    if (password.length() < 8) {
      errorMessages.add("must be at least 8 characters long");
      isValid = false;
    }
    if (password.matches(".*\\s.*")) {
      errorMessages.add("must not contain whitespace");
      isValid = false;
    }
    if (!password.matches(".*\\d.*")) {
      errorMessages.add("must contain at least one digit");
      isValid = false;
    }
    if (!password.matches(".*[A-Z].*")) {
      errorMessages.add("must contain at least one uppercase letter");
      isValid = false;
    }
    if (!password.matches(".*[a-z].*")) {
      errorMessages.add("must contain at least one lowercase letter");
      isValid = false;
    }
    if (!isValid) {
      constraintValidatorContext.disableDefaultConstraintViolation();
      constraintValidatorContext.buildConstraintViolationWithTemplate("Invalid password: " + String.join(", ", errorMessages)).addConstraintViolation();
    }
    return isValid;
  }
}
