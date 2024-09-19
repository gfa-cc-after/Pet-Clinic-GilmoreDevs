package com.greenfoxacademy.backend.repositories;

import com.greenfoxacademy.backend.models.ClinicAddress;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * ClinicAddressRepository provides database access methods for ClinicAddress entities.
 * It extends JpaRepository to perform CRUD operations.
 */
public interface ClinicAddressRepository extends JpaRepository<ClinicAddress, Long> {

  /**
   * Retrieves a list of clinic addresses based on partial matches for zip code, city, or street.
   *
   * @param zip    the partial or full zip code of the clinic address.
   * @param city   the partial or full city name of the clinic address.
   * @param street the partial or full street name of the clinic address.
   * @return a list of clinic addresses matching the given criteria.
   */
  List<ClinicAddress> findAllByZipContainingOrCityContainingOrStreetContaining(
          Integer zip, String city, String street);
}
