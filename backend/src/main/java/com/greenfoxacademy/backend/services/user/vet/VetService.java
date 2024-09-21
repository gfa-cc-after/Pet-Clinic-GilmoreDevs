package com.greenfoxacademy.backend.services.user.vet;

import com.greenfoxacademy.backend.dtos.VetListResponseDto;

/**
 * VetService defines the business logic for handling veterinarian-related data and
 * operations.
 * This interface provides methods for retrieving veterinarian data.
 */

public interface VetService {
  VetListResponseDto getAll(String word);
}
