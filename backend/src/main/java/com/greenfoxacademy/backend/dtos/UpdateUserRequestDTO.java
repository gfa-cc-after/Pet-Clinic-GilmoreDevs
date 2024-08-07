package com.greenfoxacademy.backend.dtos;

import com.greenfoxacademy.backend.services.ValidPassword;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record UpdateUserRequestDTO(
        @Email
        String email,
        @NotBlank
        String firstName,
        @NotBlank
        String lastName,
        @ValidPassword
        String password) {
}
