package com.greenfoxacademy.backend.controller;

import com.greenfoxacademy.backend.services.user.vet.VetService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class VetController {

  private final VetService vetService;

  @GetMapping("/search-vet")
  public ResponseEntity<?> searchVet() {
    return ResponseEntity.status(HttpStatus.OK).body(vetService.getAll());
  }
}
