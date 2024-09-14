package com.greenfoxacademy.backend.services.pet;

import com.greenfoxacademy.backend.dtos.AddPetResponseDto;
import com.greenfoxacademy.backend.dtos.CreatePetDto;
import com.greenfoxacademy.backend.dtos.PetDetailsDto;
import com.greenfoxacademy.backend.dtos.PetListResponseDto;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

/**
 * Retrieves the pets of the specified owner.
 *
 * @param name The name of the owner.
 * @return A response containing the owner's pets.
 */

public interface PetService {
  PetListResponseDto getOwnerPets(String name);

  AddPetResponseDto addPet(String name, CreatePetDto createPetDto);
}
