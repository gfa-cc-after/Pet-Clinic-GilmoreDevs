package com.greenfoxacademy.backend.dtos;


import java.io.Serializable;

/**
 * This class is responsible for the response of the login endpoint.
 *
 * @param token the token that is returned to identify the user
 */

public record LoginResponseDto(
        String token
) implements Serializable {
}
