package com.greenfoxacademy.backend.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.greenfoxacademy.backend.models.Pet;
import com.greenfoxacademy.backend.services.vet.VetService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class VetController {

    private final VetService vetService;

    @GetMapping("/vet")
    public ResponseEntity<?> generateVisitDocument() {
        return ResponseEntity.ok(vetService.generateVisitDocument(null));
    }
}
