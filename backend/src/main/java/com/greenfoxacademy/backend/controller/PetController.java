package com.greenfoxacademy.backend.controller;

import com.greenfoxacademy.backend.dtos.PetListResponseDto;
import com.greenfoxacademy.backend.services.pet.PetService;
import java.security.Principal;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
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

  @GetMapping("/pets")
  public ResponseEntity<PetListResponseDto> getPets(Principal owner) {
    return ResponseEntity.status(HttpStatus.OK).body(petService.getOwnerPets(owner.getName()));
  }
}