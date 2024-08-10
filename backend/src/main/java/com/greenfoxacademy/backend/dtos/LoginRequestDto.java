package com.greenfoxacademy.backend.dtos;

import com.greenfoxacademy.backend.validators.ValidPassword;
import jakarta.validation.constraints.Email;

/**
 * This class is responsible for the request of the login endpoint.
 *
 * @param email the email of the user
 * @param password the password of the user
 */
public record LoginRequestDto(
        @Email
        String email,
        @ValidPassword
        String password
) {
}
