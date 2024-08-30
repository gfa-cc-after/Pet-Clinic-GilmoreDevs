package com.greenfoxacademy.backend.repositories;

import com.greenfoxacademy.backend.models.Pet;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repository to manage Pet entities.
 */
public interface petRepository extends JpaRepository<Pet, Integer> {
}
