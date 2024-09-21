package com.greenfoxacademy.backend.services.pet;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.greenfoxacademy.backend.models.Owner;
import com.greenfoxacademy.backend.models.Pet;
import com.greenfoxacademy.backend.repositories.OwnerRepository;
import com.greenfoxacademy.backend.repositories.PetRepository;
import jakarta.transaction.Transactional;
import java.util.Arrays;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

/**
 * Integration test class for PetServiceImpl.
 * This class uses the following annotations:
 * - {@link SpringBootTest}: Indicates that the class is a Spring Boot test that
 * will start the full application context.
 * - {@link AutoConfigureMockMvc}: Automatically configures MockMvc for testing web layer.
 * - {@link Transactional}: Ensures that each test method runs within a transaction
 * that is rolled back after the test completes.
 */
@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class PetServiceImplTest {

  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private PetRepository petRepository;

  @Autowired
  private OwnerRepository ownerRepository;

  /**
   * Sets up mock data before each test.
   * This method is executed before each test method in the current test class.
   * It initializes mock data for owners and pets and saves them to the repository.
   */
  @BeforeEach
  public void setUp() {
    // Set up mock data
    Owner userWithPets = new Owner();
    userWithPets.setEmail("userWithPets@example.com");
    userWithPets.setPassword("Password");
    Owner userWithNoPets = new Owner();
    userWithNoPets.setEmail("userWithNoPets@example.com");
    userWithNoPets.setPassword("Password");

    ownerRepository.saveAll(Arrays.asList(userWithPets, userWithNoPets));

    Pet pet1 = new Pet();
    pet1.setName("Morzsi");
    pet1.setOwner(userWithPets);
    Pet pet2 = new Pet();
    pet2.setName("Rusty");
    pet2.setOwner(userWithPets);

    petRepository.saveAll(Arrays.asList(pet1, pet2));
  }

  @Test
  @WithMockUser(username = "userWithPets@example.com")
  public void testCorrectEmailWithExistingPets() throws Exception {
    mockMvc.perform(get("/pets")
        .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.pets[0].name").value("Morzsi"))
        .andExpect(jsonPath("$.pets[1].name").value("Rusty"));
  }

  @Test
  @WithMockUser(username = "userWithNoPets@example.com")
  public void testCorrectEmailWithNoExistingPets() throws Exception {
    mockMvc.perform(get("/pets")
        .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.pets").isEmpty());
  }

  @Test
  @WithMockUser(username = "nonExistingUser@example.com")
  public void testIncorrectEmail() throws Exception {
    mockMvc.perform(get("/pets")
        .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().is4xxClientError());
  }
}
