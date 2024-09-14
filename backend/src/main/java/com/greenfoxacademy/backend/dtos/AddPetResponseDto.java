package com.greenfoxacademy.backend.dtos;

/**
 * A Data Transfer Object (DTO) for the response after adding a new pet.
 * Contains the ID of the newly added pet.
 *
 * @param id the ID of the newly added pet
 */
public record AddPetResponseDto(
    Integer id
) {
}
