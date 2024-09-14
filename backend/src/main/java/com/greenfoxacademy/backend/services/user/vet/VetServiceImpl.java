package com.greenfoxacademy.backend.services.user.vet;

import com.greenfoxacademy.backend.dtos.VetDetailsDto;
import com.greenfoxacademy.backend.dtos.VetListResponseDto;
import com.greenfoxacademy.backend.models.Vet;
import com.greenfoxacademy.backend.repositories.VetRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;

import java.util.List;
import java.util.stream.Collectors;

/**
 * VetServiceImpl is the implementation of the VetService interface.
 * It provides functionality for retrieving veterinarian data.
 */

@RequiredArgsConstructor
public class VetServiceImpl implements VetService {

  private final VetRepository vetRepository;
  private final ModelMapper modelMapper;

  @Override
  public VetListResponseDto getAll() {
    List<Vet> vetList = vetRepository.findAll();

    List<VetDetailsDto> vetDtoList = vetList.stream()
            .map(vet -> modelMapper.map(vet, VetDetailsDto.class))
            .collect(Collectors.toList());

    return new VetListResponseDto(vetDtoList);
  }
}
