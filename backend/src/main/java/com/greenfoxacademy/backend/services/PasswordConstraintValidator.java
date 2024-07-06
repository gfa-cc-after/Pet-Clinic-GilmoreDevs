package com.greenfoxacademy.backend.services;

import org.passay.*;
import com.google.common.base.Joiner;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Arrays;

public class PasswordConstraintValidator implements ConstraintValidator<ValidPassword, String> {
  
  @Override
  public void initialize(ValidPassword constraintAnnotation) {
    // Initialization code, if required
  }
  
  @Override
  public boolean isValid(String password, ConstraintValidatorContext context) {
    PasswordValidator validator = new PasswordValidator(Arrays.asList(
            new LengthRule(8, 30),
//            new UppercaseCharacterRule(1),
//            new DigitCharacterRule(1),
//            new SpecialCharacterRule(1),
//            new NumericalSequenceRule(3, false),
//            new AlphabeticalSequenceRule(3, false),
//            new QwertySequenceRule(3, false),
            new WhitespaceRule() // Disallow whitespace
    ));
    
    RuleResult result = validator.validate(new PasswordData(password));
    if (result.isValid()) {
      return true;
    }
    
    context.disableDefaultConstraintViolation();
    context.buildConstraintViolationWithTemplate(
                    Joiner.on(",").join(validator.getMessages(result)))
            .addConstraintViolation();
    return false;
  }
}
