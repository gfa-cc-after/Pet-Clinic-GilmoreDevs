package com.greenfoxacademy.backend.services.pet;

import com.greenfoxacademy.backend.dtos.PetDetailsDto;
import com.greenfoxacademy.backend.dtos.PetListResponseDto;
import com.greenfoxacademy.backend.models.Pet;
import com.greenfoxacademy.backend.repositories.PetRepository;
import com.greenfoxacademy.backend.services.user.owner.OwnerService;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * Retrieves a list of pets owned by the user with the specified email.
 */
@Service
@RequiredArgsConstructor
public class PetServiceImpl implements PetService {
  private final PetRepository petRepository;
  private final OwnerService ownerService;
  private final ModelMapper modelMapper = new ModelMapper();

  /**
   * Retrieves a list of pets owned by the user with the specified email.
   *
   * @param email the email of the pet owner
   * @return a {@link PetListResponseDto} containing the list of pets
   * @throws UsernameNotFoundException if the user with the specified email is not found
   */
  @Override
  public PetListResponseDto getOwnerPets(String email) {
    List<Pet> petList = petRepository
        .findAllByOwnerId(ownerService.findByEmail(email).getId());

    List<PetDetailsDto> petDtoList = petList.stream()
        .map(pet -> modelMapper.map(pet, PetDetailsDto.class))
        .collect(Collectors.toList());

    return new PetListResponseDto(petDtoList);
  }

  /**
   * Adds a new pet for the specified owner.
   *
   * @param email the email of the owner
   * @param petDetailsDto the details of the pet to be added
   * @return the added pet details as a PetListResponseDto
   */
  @Override
  public PetListResponseDto addPet(String email, PetDetailsDto petDetailsDto) {
    Pet pet = modelMapper.map(petDetailsDto, Pet.class);
    pet.setOwner(ownerService.findByEmail(email));
    petRepository.save(pet);
    return modelMapper.map(pet, PetListResponseDto.class) ;
  }
}
