package com.greenfoxacademy.backend.repositories;

import com.greenfoxacademy.backend.models.Vet;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VetRepository extends JpaRepository<Vet,Long> {
}
