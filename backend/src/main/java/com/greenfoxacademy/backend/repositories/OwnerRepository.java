package com.greenfoxacademy.backend.repositories;

import com.greenfoxacademy.backend.models.Owner;
import com.greenfoxacademy.backend.models.User;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repository to manage {@link User} entities.
 */
public interface OwnerRepository extends JpaRepository<Owner, Integer> {
  boolean existsByEmail(String email);

  Optional<Owner> findByEmail(String email);

  void deleteByEmail(String email);

  Optional<Owner> findByVerificationId(UUID id);
}
