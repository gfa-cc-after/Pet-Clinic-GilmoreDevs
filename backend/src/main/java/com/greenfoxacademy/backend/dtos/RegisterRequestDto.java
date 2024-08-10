package com.greenfoxacademy.backend.dtos;

import com.greenfoxacademy.backend.validators.ValidPassword;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

/**
 * To create RegisterRequestDto class.
 */

public record RegisterRequestDto(
        @NotBlank
        String firstName,
        @NotBlank
        String lastName,
        @Email
        String email,
        @ValidPassword
        String password
) {
}