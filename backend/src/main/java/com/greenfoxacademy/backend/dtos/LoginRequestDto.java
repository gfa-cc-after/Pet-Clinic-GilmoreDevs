package com.greenfoxacademy.backend.dtos;

/**
 * This class is responsible for the request of the login endpoint.
 *
 * @param email the email of the user
 * @param password the password of the user
 */
public record LoginRequestDto(
        String email,
        String password
) {
}
