package com.greenfoxacademy.backend.dtos;

import jakarta.validation.constraints.NotBlank;
import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * A data transfer object for pet details.
 *
 * @author Your Name
 */
public record PetListRequestDto(
    @NotBlank
    String petName,
    @NotBlank
    String petBreed,
    @NotBlank
    String petSex,
    @NotBlank
    Date petBirthDate,
    Date lastCheckUp,
    Date nextCheckUp
) {
}
