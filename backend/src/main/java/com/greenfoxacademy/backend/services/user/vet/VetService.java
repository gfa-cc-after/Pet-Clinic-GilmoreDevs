package com.greenfoxacademy.backend.services.user.vet;

import com.greenfoxacademy.backend.dtos.VetListResponseDto;
import org.springframework.stereotype.Service;

/**
 * VetService defines the business logic for handling veterinarian-related data and
 * operations.
 * This interface provides methods for retrieving veterinarian data.
 */

@Service
public interface VetService {

  VetListResponseDto getAll();
}
