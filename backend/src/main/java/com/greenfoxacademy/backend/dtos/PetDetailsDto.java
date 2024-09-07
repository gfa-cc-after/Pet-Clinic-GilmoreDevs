package com.greenfoxacademy.backend.dtos;

import jakarta.validation.constraints.NotBlank;
import java.util.Date;
import lombok.Data;

/**
 * A data transfer object for pet details.
 *
 * @author Your Name
 */
@Data
public class PetDetailsDto {
  @NotBlank
  String name;
  @NotBlank
  String breed;
  @NotBlank
  String sex;
  @NotBlank
  Date birthDate;
  Date lastCheckUp;
  Date nextCheckUp;
}
