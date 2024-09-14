package com.greenfoxacademy.backend.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PastOrPresent;
import java.util.Date;

/**
 * A Data Transfer Object (DTO) for creating a new pet.
 * Contains the necessary information to create a pet, including name, breed, sex, and birth date.
 *
 * <p>Validation constraints are applied to ensure the data integrity:
 * <ul>
 *   <li>{@link NotBlank} ensures that the name, breed, and sex fields are not blank.</li>
 *   <li>{@link PastOrPresent} ensures that the birth date is not in the future.</li>
 * </ul>
 * </p>
 *
 * @param name the name of the pet, must not be blank
 * @param breed the breed of the pet, must not be blank
 * @param sex the sex of the pet, must not be blank
 * @param birthDate the birth date of the pet, must be in the past or present
 */
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
