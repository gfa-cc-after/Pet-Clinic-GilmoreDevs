package com.greenfoxacademy.backend.controller;

import com.greenfoxacademy.backend.dtos.AddPetResponseDto;
import com.greenfoxacademy.backend.dtos.CreatePetDto;
import com.greenfoxacademy.backend.dtos.PetListResponseDto;
import com.greenfoxacademy.backend.services.pet.PetService;
import java.security.Principal;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * A REST controller that handles operations related to users' pets.
 *
 * @author Your Name
 */
@RequiredArgsConstructor
@RestController
public class PetController {
  private final PetService petService;

  /**
   * Retrieves the list of pets owned by the authenticated user.
   *
   * @param owner the authenticated user's principal
   * @return a {@link ResponseEntity} containing a {@link PetListResponseDto} with the list of pets
   */
  @GetMapping("/pets")
  public ResponseEntity<PetListResponseDto> getPets(Principal owner) {
    return ResponseEntity.status(HttpStatus.OK).body(petService.getOwnerPets(owner.getName()));
  }

  /**
    * Adds a new pet for the authenticated user.
    *
    * @param owner the authenticated user's principal
    * @param createPetDto the details of the pet to be added
    * @return a {@link ResponseEntity} containing an {@link AddPetResponseDto}
    with the added pet's details
    */
  @PostMapping("/pets")
  public ResponseEntity<AddPetResponseDto> addPet(Principal owner,
                                                  @RequestBody CreatePetDto createPetDto) {
    return ResponseEntity
      .status(HttpStatus.OK)
      .body(petService.addPet(owner.getName(), createPetDto));
  }
}
