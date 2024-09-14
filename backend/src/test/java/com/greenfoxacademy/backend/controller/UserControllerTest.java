package com.greenfoxacademy.backend.controller;

import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.greenfoxacademy.backend.dtos.EmailSentDto;
import com.greenfoxacademy.backend.dtos.LoginRequestDto;
import com.greenfoxacademy.backend.dtos.LoginResponseDto;
import com.greenfoxacademy.backend.errors.UserAlreadyExistsError;
import com.greenfoxacademy.backend.models.Owner;
import com.greenfoxacademy.backend.repositories.OwnerRepository;
import com.greenfoxacademy.backend.services.mail.EmailService;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

@SpringBootTest
@AutoConfigureMockMvc
class UserControllerTest {

  /**
   * The UserRepository is mocked, so we can define its behavior in each test.
   */
  @MockBean
  private OwnerRepository ownerRepository;

  /**
   * The MockMvc is used to perform a request to the controller and validate the response.
   */
  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private EmailService emailService;

  /**
   * The PasswordEncoder is used to encode the password before saving it to the database.
   * The password is encoded using the BCrypt algorithm.
   */
  @Autowired
  private PasswordEncoder passwordEncoder;
  @Autowired
  private ObjectMapper jacksonObjectMapper;

  @DisplayName("Should return password is not present when password is not present")
  @Test
  void shouldReturnPasswordIsNotPresentWhenPasswordIsNotPresent() throws Exception {
    String content = """
            {
                "firstName": "John",
                "lastName": "Doe",
                "email": "john@doe.com"
            }
            """;
    this.mockMvc.perform(post("/register")
                    .contentType(MediaType.APPLICATION_JSON).content(content))
            .andExpectAll(
                    status().isBadRequest(),
                    jsonPath("$.password", is("Password should be added")));
  }

  @DisplayName("Should return password is short when password is short")
  @Test
  void shouldReturnPasswordIsNotLongEnoughWhenPasswordIsShort() throws Exception {
    String content = """
            {
                "firstName": "John",
                "lastName": "Doe",
                "email": "john@doe.com",
                "password": "1Aa"
            }
            """;
    this.mockMvc.perform(post("/register")
                    .contentType(MediaType.APPLICATION_JSON).content(content))
            .andExpectAll(
                    status().isBadRequest(),
                    jsonPath(
                            "$.password",
                            is("Invalid password: must be at least 8 characters long")));
  }

  @DisplayName("Should return password has no digit when password has no digit")
  @Test
  void shouldReturnPasswordContainsNoDigitsIfItContainsNoDigits() throws Exception {
    String content = """
            {
                "firstName": "John",
                "lastName": "Doe",
                "email": "john@doe.com",
                "password": "aAaaaaaaaaaaaaaaaaaaaaaa"
            }
            """;
    this.mockMvc.perform(post("/register")
                    .contentType(MediaType.APPLICATION_JSON).content(content))
            .andExpectAll(
                    status().isBadRequest(),
                    jsonPath(
                            "$.password",
                            is("Invalid password: must contain at least one digit")));
  }

  @DisplayName("Should return password has no uppercase when password has no uppercase")
  @Test
  void shouldReturnPasswordContainsNoUpperCaseIfPasswordContainsNoUppercase() throws Exception {
    String content = """
            {
                "firstName": "John",
                "lastName": "Doe",
                "email": "john@doe.com",
                "password": "1aaaaaaaaaaaaaaaaaaaaaaa"
            }
            """;
    this.mockMvc.perform(post("/register")
                    .contentType(MediaType.APPLICATION_JSON).content(content))
            .andExpectAll(
                    status().isBadRequest(),
                    jsonPath(
                            "$.password",
                            is("Invalid password: must contain at least one uppercase letter")
                    ));
  }

  @DisplayName("Should return password has no lowercase when password has no lowercase")
  @Test
  void shouldReturnPasswordContainsNoLowerCaseIfPasswordContainsNoLowercase() throws Exception {
    String content = """
            {
                "firstName": "John",
                "lastName": "Doe",
                "email": "john@doe.com",
                "password": "1AAAAAAAAAAAAAAAAAAAAAA"
            }
            """;
    this.mockMvc.perform(post("/register")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(content))
            .andExpectAll(status().isBadRequest(),
                    jsonPath(
                            "$.password",
                            is("Invalid password: must contain at least one lowercase letter")));
  }

  @DisplayName("Should return password contains white space when password contains white space")
  @Test
  void shouldReturnPasswordContainsWhiteSpaceIfContainsWhiteSpace() throws Exception {
    String content = """
            {
                "firstName": "John",
                "lastName": "Doe",
                "email": "john@doe.com",
                "password": "1 A c a 2 b B a 2"
            }
            """;
    this.mockMvc.perform(post("/register")
                    .contentType(MediaType.APPLICATION_JSON).content(content))
            .andExpectAll(
                    status().isBadRequest(),
                    jsonPath(
                            "$.password",
                            is("Invalid password: must not contain whitespace")));
  }

  @DisplayName("Should return password is invalid with all errors if it has all errors")
  @Test
  void shouldReturnPasswordIsInvalidWithAllErrorsIfItHasAllErrors() throws Exception {
    String content = """
            {
                "firstName": "John",
                "lastName": "Doe",
                "email": "john@doe.com",
                "password": "  "
            }
            """;
    this.mockMvc.perform(post("/register")
                    .contentType(MediaType.APPLICATION_JSON).content(content))
            .andExpectAll(
                    status().isBadRequest(),
                    jsonPath(
                            "$.password",
                            is("Invalid password: must be at least 8 characters long,"
                                    + " must not contain whitespace,"
                                    + " must contain at least one digit,"
                                    + " must contain at least one uppercase letter,"
                                    + " must contain at least one lowercase letter")));
  }

  @DisplayName("Should return email is not valid when email in not valid")
  @Test
  void shouldReturnEmailIsInvalidWhenEmailIsInvalid() throws Exception {
    String content = """
            {
                "firstName": "John",
                "lastName": "Doe",
                "email": "john.doe.com",
                "password": "Aa2aaaaaBBBBaaaaa"
            }
            """;
    this.mockMvc.perform(post("/register")
                    .contentType(MediaType.APPLICATION_JSON).content(content))
            .andExpectAll(
                    status().isBadRequest(),
                    jsonPath(
                            "$.email",
                            is("must be a well-formed email address")));
  }

  @DisplayName("Should return email is duplicated when email is duplicated")
  @Test
  void shouldReturnEmailIsIsDuplicatedWhenEmailIsDuplicated() throws Exception {
    when(ownerRepository.save(Mockito.any()))
            .thenThrow(new UserAlreadyExistsError("Email is already taken!"));
    String content = """
            {
                "firstName": "John",
                "lastName": "Doe",
                "email": "asd@asd.com",
                "password": "Aa2aaaaaBBBBaaaaa"
            }
            """;
    this.mockMvc.perform(post("/register")
                    .contentType(MediaType.APPLICATION_JSON).content(content))
            .andExpectAll(
                    status().isBadRequest(),
                    jsonPath("$.error").value("Email is already taken!"));
  }

  @DisplayName("Should created object")
  @Test
  void shouldReturnUserSuccessfullyCreatedIfEverythingIsCorrect() throws Exception {

    when(ownerRepository.save(Mockito.any())).thenReturn(Owner.builder().id(1L).build());
    when(emailService.sendRegistrationEmail(anyString(), anyString(), Mockito.any()))
            .thenReturn(new EmailSentDto());
    String content = """
            {
                "firstName": "John",
                "lastName": "Doe",
                "email": "asd@asd.com",
                "password": "Aa2aaaaaBBBBaaaaa"
            }
            """;
    this.mockMvc.perform(post("/register")
                    .contentType(MediaType.APPLICATION_JSON).content(content))
            .andExpectAll(
                    status().isOk(),
                    jsonPath("$.id").isNumber());
  }

  @DisplayName("Should return a token when a user is successfully logged in with good credentials")
  @Test
  void shouldReturnTokenWhenUserIsSuccessfullyLoggedInWithGoodCredentials() throws Exception {

    when(ownerRepository.existsByEmail("johndoe@gmail.com")).thenReturn(true);
    when(ownerRepository.findByEmail("johndoe@gmail.com"))
            .thenReturn(Optional
                    .of(Owner.builder()
                            .id(1L)
                            .firstName("John")
                            .lastName("Doe")
                            .email("johndoe@gmail.com")
                            .password(passwordEncoder.encode("AValidPassword1")).build()));
    String content = """
            {
                "email": "johndoe@gmail.com",
                "password": "AValidPassword1"
            }
            """;
    this.mockMvc.perform(post("/login")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(content))
            .andExpectAll(
                    status().isOk(),
                    content().contentType(MediaType.APPLICATION_JSON),
                    jsonPath("$.token").isString());
  }

  @DisplayName("Should return unauthenticated when user is not found by email")
  @Test
  void shouldReturnUnauthenticatedWhenEmailIsNotFound() throws Exception {

    when(ownerRepository.existsByEmail("johndoe@gmail.com")).thenReturn(false);
    String content = """
            {
                "email": "johndoe@gmail.com",
                "password": "AValidPassword1"
            }
            """;
    this.mockMvc.perform(post("/login")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(content))
            .andExpectAll(
                    status().isUnauthorized());
  }

  @DisplayName("Should not be able to update profile if not logged in")
  @Test
  void shouldNotBeAbleToUpdateProfileIfNotLoggedIn() throws Exception {
    this.mockMvc.perform(patch("/profile-update")
                    .contentType(MediaType.APPLICATION_JSON))
            .andExpectAll(status().isForbidden());
  }

  @DisplayName("Should be able to update profile if logged in")
  @Test
  @WithMockUser(username = "john.doe@gmail.com", password = "password", roles = "USER")
  void shouldBeAbleToUpdateProfileIfLoggedIn() throws Exception {
    String email = "john.doe@gmail.com";
    when(ownerRepository.findByEmail("john.doe@gmail.com"))
            .thenReturn(Optional.of(Owner.builder()
                    .id(1L)
                    .email(email)
                    .firstName("John")
                    .lastName("Doe")
                    .password(passwordEncoder.encode("password"))
                    .build()));

    when(ownerRepository.save(Mockito.any()))
            .thenReturn(Owner.builder()
                    .id(1L)
                    .email(email)
                    .firstName("John")
                    .lastName("Doe")
                    .password(passwordEncoder.encode("password"))
                    .build());

    MvcResult response = this.mockMvc.perform(post("/login")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content("""
                            {
                                "email": "john.doe@gmail.com",
                                "password": "password"
                            }
                            """))
            .andDo(result -> {
              String contentAsString = result.getResponse().getContentAsString();
              System.out.println(contentAsString);
            })
            .andReturn();

    LoginResponseDto loginResponseDto = jacksonObjectMapper
            .readValue(response.getResponse().getContentAsString(),
                    LoginResponseDto.class);

    String content = """
            {
                "email": "john.doe@gmail.com",
                "firstName": "John",
                "lastName": "Doe",
                "password": "Aa2aaaaaBBBBaaaaa"
            }
            """;
    this.mockMvc.perform(patch("/profile-update")
                    .header("Authorization", "Bearer " + loginResponseDto.token())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(content))
            .andExpectAll(status().isOk());
  }

  @Test
  @DisplayName("UserShould delete profile")
  void deleteProfile() throws Exception {
    LoginRequestDto loginRequestDto = new LoginRequestDto("john.doe@gmail.com",
            "password");
    when(ownerRepository.existsByEmail(loginRequestDto.email())).thenReturn(true);
    when(ownerRepository.findByEmail(loginRequestDto.email()))
            .thenReturn(Optional.of(Owner.builder()
                    .id(1L)
                    .email(loginRequestDto.email())
                    .firstName("John")
                    .lastName("Doe")
                    .password(passwordEncoder.encode(loginRequestDto.password()))
                    .build()
            ));

    MvcResult result = mockMvc.perform(
                    post("/login")
                            .content(jacksonObjectMapper.writeValueAsString(loginRequestDto))
                            .contentType(MediaType.APPLICATION_JSON)
            )
            .andReturn();

    LoginResponseDto loginResponseDto = jacksonObjectMapper.readValue(
            result.getResponse().getContentAsString(),
            LoginResponseDto.class);

    mockMvc.perform(delete("/delete-profile")
                    .contentType(MediaType.APPLICATION_JSON)
                    .header("Authorization", "Bearer " + loginResponseDto.token())
            )
            .andExpect(status().isAccepted());

    Mockito.verify(ownerRepository, Mockito.times(1))
            .deleteByEmail(loginRequestDto.email());
  }
}
