package com.greenfoxacademy.backend.dtos;

import com.greenfoxacademy.backend.services.ValidPassword;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Data Transfer Object for User Entity.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegisterUserDto {
  private String firstName;
  private String lastName;
  private String email;
  @ValidPassword
  private String password;
}
