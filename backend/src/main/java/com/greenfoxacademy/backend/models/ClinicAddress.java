package com.greenfoxacademy.backend.models;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.Id;
import lombok.Data;

/**
 * Represents an address for a clinic.
 * <p>
 * The {@code ClinicAddress} class is used as an embeddable type in JPA entities to store address
 * details of a clinic. It includes information such as the street address, city, postal code,
 * and clinic name.
 * </p>
 * <p>
 * This class is marked with {@code @Embeddable} to indicate that it can be embedded in other JPA
 * entities.
 * </p>
 * <p>
 * The {@code id} field is used as a unique identifier for the address. This field is optional and
 * is provided for cases where a unique identifier is necessary.
 * </p>
 * <p>
 * The {@code zip} field represents the postal code for the clinic address and is limited to a
 * length of 4 digits.
 * </p>
 * <p>
 * The {@code clinicName} field specifies the name of the clinic associated with the address.
 * </p>
 *
 * @see jakarta.persistence.Embeddable
 */

@Embeddable
@Data
public class ClinicAddress {
  @Id
  private Long id;
  private String city;
  @Column(length = 4)
  private int zip;
  private String street;
  private double longitude;
  private double latitude;




}
