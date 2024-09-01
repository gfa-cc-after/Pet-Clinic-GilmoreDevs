package com.greenfoxacademy.backend.dtos;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * A data transfer object for a list of pets.
 *
 */
public record PetListResponseDto(
    List<PetListRequestDto> pets
){
}
