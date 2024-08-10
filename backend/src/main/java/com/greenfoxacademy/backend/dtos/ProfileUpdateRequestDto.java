package com.greenfoxacademy.backend.dtos;

import com.greenfoxacademy.backend.validators.ValidPassword;
import jakarta.validation.constraints.Email;

/**
 * To create ProfileUpdateRequestDto class.
 */

public record ProfileUpdateRequestDto(
        String firstName,
        String lastName,
        @Email
        String email,
        @ValidPassword
        String password
) {
}
