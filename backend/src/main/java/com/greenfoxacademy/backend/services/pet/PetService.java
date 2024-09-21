package com.greenfoxacademy.backend.services.pet;

import com.greenfoxacademy.backend.dtos.PetListResponseDto;

/**
 * Retrieves the pets of the specified owner.
 *
 * @param name The name of the owner.
 * @return A response containing the owner's pets.
 */

public interface PetService {
  PetListResponseDto getOwnerPets(String name);
}
