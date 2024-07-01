package com.greenfoxacademy.backend.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Data Transfer Object for User Entity.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegisterUserDTO {
  private String firstName;
  private String lastName;
  private String email;
  private String password;
}
