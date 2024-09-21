package com.greenfoxacademy.backend.repositories;

import com.greenfoxacademy.backend.models.ClinicDetails;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;


/**
 * ClinicDetailsRepository provides database access methods for ClinicDetails entities.
 * It extends JpaRepository to perform CRUD operations.
 */
public interface ClinicDetailsRepository extends JpaRepository<ClinicDetails, Long> {

  /**
   * Retrieves a list of clinic details where the clinic name contains the specified word.
   *
   * @param word a partial or full word to search within clinic names.
   * @return a list of clinic details matching the specified word in their names.
   */
  List<ClinicDetails> findAllByClinicNameContaining(String word);
}
