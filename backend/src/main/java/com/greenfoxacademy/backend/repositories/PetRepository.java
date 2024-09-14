package com.greenfoxacademy.backend.repositories;

import com.greenfoxacademy.backend.models.Pet;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repository to manage Pet entities.
 */
public interface PetRepository extends JpaRepository<Pet, Long> {
  List<Pet> findAllByOwnerId(Integer ownerId);
}
