package com.greenfoxacademy.backend.controller;

import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.*;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.greenfoxacademy.backend.dtos.UpdateUserRequestDTO;
import com.greenfoxacademy.backend.errors.UserAlreadyExistsError;
import com.greenfoxacademy.backend.models.User;
import com.greenfoxacademy.backend.repositories.UserRepository;

import java.util.Optional;

import com.greenfoxacademy.backend.utils.WithMockCustomUser;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.MockMvcPrint;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.ApplicationContext;
import org.springframework.http.MediaType;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@SpringBootTest
@AutoConfigureMockMvc
class UserControllerTest {

  @Autowired
  WebApplicationContext context;

  /**
   * The UserRepository is mocked, so we can define its behavior in each test.
   */
  @MockBean
  private UserRepository userRepository;

  /**
   * The MockMvc is used to perform a request to the controller and validate the response.
   */
  @Autowired
  private MockMvc mockMvc;

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
    this.mockMvc.perform(post("/api/user/register").contentType(MediaType.APPLICATION_JSON).content(content)).andExpectAll(status().isBadRequest(), jsonPath("$.password", is("Password should be added")));
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
    this.mockMvc.perform(post("/api/user/register").contentType(MediaType.APPLICATION_JSON).content(content)).andExpectAll(status().isBadRequest(), jsonPath("$.password", is("Invalid password: must be at least 8 characters long")));
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
    this.mockMvc.perform(post("/api/user/register").contentType(MediaType.APPLICATION_JSON).content(content)).andExpectAll(status().isBadRequest(), jsonPath("$.password", is("Invalid password: must contain at least one digit")));
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
    this.mockMvc.perform(post("/api/user/register").contentType(MediaType.APPLICATION_JSON).content(content)).andExpectAll(status().isBadRequest(), jsonPath("$.password", is("Invalid password: must contain at least one uppercase letter")));
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
    this.mockMvc.perform(post("/api/user/register").contentType(MediaType.APPLICATION_JSON).content(content)).andExpectAll(status().isBadRequest(), jsonPath("$.password", is("Invalid password: must contain at least one lowercase letter")));
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
    this.mockMvc.perform(post("/api/user/register").contentType(MediaType.APPLICATION_JSON).content(content)).andExpectAll(status().isBadRequest(), jsonPath("$.password", is("Invalid password: must not contain whitespace")));
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
    this.mockMvc.perform(post("/api/user/register").contentType(MediaType.APPLICATION_JSON).content(content)).andExpectAll(status().isBadRequest(), jsonPath("$.password", is("Invalid password: must be at least 8 characters long," + " must not contain whitespace, must contain at least one digit," + " must contain at least one uppercase letter," + " must contain at least one lowercase letter")));
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
    this.mockMvc.perform(post("/api/user/register").contentType(MediaType.APPLICATION_JSON).content(content)).andExpectAll(status().isBadRequest(), jsonPath("$.email", is("must be a well-formed email address")));
  }

  @DisplayName("Should return email is duplicated when email is duplicated")
  @Test
  void shouldReturnEmailIsIsDuplicatedWhenEmailIsDuplicated() throws Exception {
    when(userRepository.save(Mockito.any())).thenThrow(new UserAlreadyExistsError("Email is already taken!"));
    String content = """
            {
                "firstName": "John",
                "lastName": "Doe",
                "email": "asd@asd.com",
                "password": "Aa2aaaaaBBBBaaaaa"
            }
            """;
    this.mockMvc.perform(post("/api/user/register").contentType(MediaType.APPLICATION_JSON).content(content)).andExpectAll(status().isBadRequest(), jsonPath("$.error").value("Email is already taken!"));
  }

  @DisplayName("Should created object")
  @Test
  void shouldReturnUserSuccessfullyCreatedIfEverythingIsCorrect() throws Exception {
    when(userRepository.save(Mockito.any())).thenReturn(User.builder().id(1).build());
    String content = """
            {
                "firstName": "John",
                "lastName": "Doe",
                "email": "asd@asd.com",
                "password": "Aa2aaaaaBBBBaaaaa"
            }
            """;
    this.mockMvc.perform(post("/api/user/register").contentType(MediaType.APPLICATION_JSON).content(content)).andExpectAll(status().isOk(), jsonPath("$.id").isNumber());
  }

  @DisplayName("Should return a token when a user is successfully logged in with good credentials")
  @Test
  void shouldReturnTokenWhenUserIsSuccessfullyLoggedInWithGoodCredentials() throws Exception {

    when(userRepository.existsByEmail("johndoe@gmail.com")).thenReturn(true);
    when(userRepository.findByEmail("johndoe@gmail.com")).thenReturn(Optional.of(User.builder().id(1).email("johndoe@gmail.com").password(passwordEncoder.encode("AValidPassword1")).build()));
    String content = """
            {
                "email": "johndoe@gmail.com",
                "password": "AValidPassword1"
            }
            """;
    this.mockMvc.perform(post("/api/user/login").contentType(MediaType.APPLICATION_JSON).content(content)).andExpectAll(status().isOk(), content().contentType(MediaType.APPLICATION_JSON), jsonPath("$.token").isString());
  }

  @DisplayName("Should return unauthenticated when user is not found by email")
  @Test
  void shouldReturnUnauthenticatedWhenEmailIsNotFound() throws Exception {

    when(userRepository.existsByEmail("johndoe@gmail.com")).thenReturn(false);
    String content = """
            {
                "email": "johndoe@gmail.com",
                "password": "AValidPassword1"
            }
            """;
    this.mockMvc.perform(post("/api/user/login").contentType(MediaType.APPLICATION_JSON).content(content)).andExpectAll(status().isUnauthorized());
  }

  @Test
  @DisplayName("Should call the repository save when updating a user")
  @WithMockCustomUser(username = "john.doe@gmail.com", roles = {"USER"}, name = "John Doe")
  void shouldCallTheRepositorySaveWhenUpdatingAUser() throws Exception {

    // Given
    User user = User.builder().id(1).email("john.doe@gmail.com").password(passwordEncoder.encode("AValidPassword1")).build();

    UpdateUserRequestDTO updateUserRequestDTO = new UpdateUserRequestDTO("john.doe@gmail.com", "John", "Doe", "AValidPassword2");

    // When
    when(userRepository.findByEmail("john.doe@gmail.com")).thenReturn(Optional.of(user));
    when(userRepository.save(Mockito.any())).thenReturn(user);

    // Then
    mockMvc.perform(patch("/api/user/profile-update").content(jacksonObjectMapper.writeValueAsString(updateUserRequestDTO)).contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk());

    Mockito.verify(userRepository, Mockito.times(1)).save(Mockito.any());
  }


  @Test
  @DisplayName("Should not call the repository save when updating a user")
  void shouldCallTheRepositorySaveWhenUpdatingAUserWithAuthentication() throws Exception {

    // Given
    User user = User.builder().id(1).email("john.doe@gmail.com").password(passwordEncoder.encode("AValidPassword1")).build();
    UpdateUserRequestDTO updateUserRequestDTO = new UpdateUserRequestDTO("john.doe@gmail.com", "John", "Doe", "AValidPassword2");

    // When
    when(userRepository.findByEmail("john.doe@gmail.com")).thenReturn(Optional.of(user));
    when(userRepository.save(Mockito.any())).thenReturn(user);

    // Then
    mockMvc.perform(patch("/api/user/profile-update").content(jacksonObjectMapper.writeValueAsString(updateUserRequestDTO)).contentType(MediaType.APPLICATION_JSON)).andExpect(status().isForbidden());

    Mockito.verify(userRepository, Mockito.times(0)).save(Mockito.any());
  }

}
