package com.greenfoxacademy.backend.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.greenfoxacademy.backend.models.Settings;

public interface SettingsRepository extends JpaRepository<Settings, Long> {
  Optional<Settings> findByUserEmail(String email);
}
