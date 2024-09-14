package com.greenfoxacademy.backend.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PastOrPresent;
import java.util.Date;

public record CreatePetDto(
  @NotBlank
  String name,
  @NotBlank
  String breed,
  @NotBlank
  String sex,
  @PastOrPresent(message = "The birth date must be in the past or present")
  Date birthDate
) {
}
