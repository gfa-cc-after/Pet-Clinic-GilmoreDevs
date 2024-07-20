package com.greenfoxacademy.backend.dtos;

import com.greenfoxacademy.backend.services.ValidPassword;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Data Transfer Object for User Entity.
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class RegisterRequestDto {
  @NotBlank
  private String firstName;
  @NotBlank
  private String lastName;
  @Email
  private String email;
  @ValidPassword
  private String password;
}
