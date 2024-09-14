package com.greenfoxacademy.backend.dtos;

import java.util.List;

/**
 * VetListResponseDto is a record that represents a list of veterinarian details.
 *
 * @param vets a list of VetDetailsDto objects containing the details of each veterinarian.
 */

public record VetListResponseDto(
        List<VetDetailsDto> vets
) {

}
