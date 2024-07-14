package com.greenfoxacademy.backend.dtos;

public record LoginRequestDto(
        String email,
        String password
) {
}
