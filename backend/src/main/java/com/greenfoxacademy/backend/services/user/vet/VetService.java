package com.greenfoxacademy.backend.services.user.vet;

import com.greenfoxacademy.backend.dtos.VetListResponseDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface VetService {

  VetListResponseDto getAll();
}
