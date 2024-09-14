package com.greenfoxacademy.backend.models;

import jakarta.persistence.Embeddable;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

/**
 * Represents the details of a clinic.
 * <p>
 * The {@code ClinicDetails} class is used as an embeddable type in JPA entities to store detailed
 * information about a clinic. This class contains the address of the clinic as well as any
 * additional details that may be relevant for clinic management.
 * </p>
 * <p>
 * This class is annotated with {@code @Embeddable} to indicate that it can be embedded within
 * other JPA entities.
 * </p>
 *
 * @see ClinicAddress
 */

@Data
@Embeddable
public class ClinicDetails {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;
  private String clinicName;
  private ClinicAddress clinicAddress;

}
