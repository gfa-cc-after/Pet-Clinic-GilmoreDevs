package com.greenfoxacademy.backend.dtos;

import com.greenfoxacademy.backend.models.Vet;

import java.util.List;

public record VetListResponseDto(
        List<VetDetailsDto> vets
) {

}
