package com.greenfoxacademy.backend.repositories;

import com.greenfoxacademy.backend.models.Vet;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * VetRepository provides database access methods for Vet entities.
 * It extends JpaRepository to perform CRUD operations.
 */
public interface VetRepository extends JpaRepository<Vet, Long> {

  /**
   * Retrieves a list of vets based on the first or last name.
   *
   * @param firstName the first name of the vet.
   * @param lastName  the last name of the vet.
   * @return a list of vets matching the given first or last name.
   */
  List<Vet> findAllByFirstNameOrLastName(String firstName, String lastName);

  /**
   * Retrieves a list of vets based on the clinic name.
   *
   * @param clinicName the name of the clinic.
   * @return a list of vets working at the clinic with the given name.
   */
  List<Vet> findAllByClinicDetailsClinicName(String clinicName);

}
