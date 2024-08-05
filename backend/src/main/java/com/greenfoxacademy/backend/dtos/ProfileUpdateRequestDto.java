package com.greenfoxacademy.backend.dtos;

public record ProfileUpdateRequestDto(
    String firstName,
    String lastName,
    String email,
    String password
) {
}
