package com.greenfoxacademy.backend.dtos;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PastOrPresent;
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
  @PastOrPresent(message = "The birth date must be in the past or present")
  Date birthDate;
  @PastOrPresent(message = "The last check-up date must be in the past or present")
  Date lastCheckUp;
  @FutureOrPresent(message = "The next check-up date must be in the future or present")
  Date nextCheckUp;
}
