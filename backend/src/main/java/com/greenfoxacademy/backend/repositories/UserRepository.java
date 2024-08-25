package com.greenfoxacademy.backend.repositories;

import com.greenfoxacademy.backend.models.User;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repository to manage {@link User} entities.
 */
public interface UserRepository extends JpaRepository<User, Integer> {
  boolean existsByEmail(String email);

  Optional<User> findByEmail(String email);

  void deleteByEmail(String email);

  Optional<User> findByVerificationID(UUID id);
}
