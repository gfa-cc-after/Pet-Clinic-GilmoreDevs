package com.greenfoxacademy.backend.dtos;

import com.greenfoxacademy.backend.services.ValidPassword;
import jakarta.validation.constraints.Email;
import lombok.Data;


/**
 * Data Transfer Object for Login user.
 */
@Data
public class LoginUserDto {
  @Email
  private String email;
  @ValidPassword
  private String password;
}
