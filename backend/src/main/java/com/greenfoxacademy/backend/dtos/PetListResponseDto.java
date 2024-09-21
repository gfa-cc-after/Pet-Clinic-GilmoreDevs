package com.greenfoxacademy.backend.dtos;

import java.util.List;

/**
 * A data transfer object for a list of pets.
 *
 */
public record PetListResponseDto(
    List<PetDetailsDto> pets
){
}
