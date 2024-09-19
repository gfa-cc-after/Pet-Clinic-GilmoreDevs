package com.greenfoxacademy.backend.services.vet;

import com.greenfoxacademy.backend.dtos.VetAnswerDTO;
import com.greenfoxacademy.backend.models.Pet;

public interface VetService {
    VetAnswerDTO generateVisitDocument(Pet subject);
}
