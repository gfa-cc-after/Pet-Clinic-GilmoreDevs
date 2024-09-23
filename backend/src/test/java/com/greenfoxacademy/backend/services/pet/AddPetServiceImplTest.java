package com.greenfoxacademy.backend.services.pet;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import com.greenfoxacademy.backend.controller.PetController;
import com.greenfoxacademy.backend.dtos.CreatePetDto;
import com.greenfoxacademy.backend.repositories.PetRepository;
import java.util.Date;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@Nested
@RequiredArgsConstructor
@ExtendWith(MockitoExtension.class)
class AddPetServiceImplTest {
  @Mock
  private PetService petService;

  @Mock
  private PetRepository petRepository;

  @InjectMocks
  private PetController petController;

  @Test
  void petSuccessfullyAddedToPetRepository() {
    CreatePetDto pet = new CreatePetDto("Morzsi", "Dog", "Male", new Date(2024, 9, 13));
    petService.addPet("owner", pet);

    verify(petService, times(1)).addPet("owner", pet);
  }
}