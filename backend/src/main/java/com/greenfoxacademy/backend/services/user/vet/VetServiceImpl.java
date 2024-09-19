package com.greenfoxacademy.backend.services.user.vet;

import com.greenfoxacademy.backend.dtos.VetDetailsDto;
import com.greenfoxacademy.backend.dtos.VetListResponseDto;
import com.greenfoxacademy.backend.models.ClinicAddress;
import com.greenfoxacademy.backend.models.ClinicDetails;
import com.greenfoxacademy.backend.models.Vet;
import com.greenfoxacademy.backend.repositories.ClinicAddressRepository;
import com.greenfoxacademy.backend.repositories.ClinicDetailsRepository;
import com.greenfoxacademy.backend.repositories.VetRepository;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

/**
 * VetServiceImpl is the implementation of the VetService interface.
 * It provides functionality for retrieving veterinarian data.
 */
@Service
@RequiredArgsConstructor
public class VetServiceImpl implements VetService {

  private final VetRepository vetRepository;
  private final ClinicAddressRepository clinicAddressRepository;
  private final ClinicDetailsRepository clinicDetailsRepository;
  private final ModelMapper modelMapper;

  @Override
  public VetListResponseDto getAll(String word) {
    List<Vet> vetList = vetRepository.findAllByFirstNameOrLastName(word, word);

    List<ClinicAddress> clinicAddressList = clinicAddressRepository
            .findAllByZipContainingOrCityContainingOrStreetContaining(
                    word , word, word);

    vetList.addAll( clinicAddressList.stream()
            .map(a -> a.getClinicDetails().getVet()).toList());

    vetList.addAll(clinicDetailsRepository.findAllByClinicNameContaining(word)
            .stream().map(ClinicDetails::getVet).toList());

    List<VetDetailsDto> vetDtoList = vetList.stream()
            .map(vet -> modelMapper.map(vet, VetDetailsDto.class))
            .collect(Collectors.toList());

    return new VetListResponseDto(vetDtoList);
  }
}
