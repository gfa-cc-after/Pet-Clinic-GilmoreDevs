package com.greenfoxacademy.backend.controller;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.greenfoxacademy.backend.models.User;
import com.greenfoxacademy.backend.repositories.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;


@SpringBootTest
@AutoConfigureMockMvc
class UserControllerTest {

  @MockBean
  private UserRepository userRepository;

  @Autowired
  private MockMvc mockMvc;


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
            jsonPath("$.password", is("Password should be added"))
        );
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
                is("Invalid password: must be at least 8 characters long"))
        );
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
                is("Invalid password: must contain at least one digit")
            )
        );
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
            )
        );
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
                is("Invalid password: must contain at least one lowercase letter")
            )
        );
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
                is("Invalid password: must not contain whitespace")
            )
        );
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
                    + " must not contain whitespace, must contain at least one digit,"
                    + " must contain at least one uppercase letter,"
                    + " must contain at least one lowercase letter")
            ));
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
                is("must be a well-formed email address")
            )
        );
  }


  @DisplayName("Should return email is duplicated when email is duplicated")
  @Test
  void shouldReturnEmailIsIsDuplicatedWhenEmailIsDuplicated() throws Exception {

    Mockito.when(userRepository.save(Mockito.any())).thenThrow(new RuntimeException("Email is already taken"));
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
            content().string("Email is already taken")
        );
  }

  @DisplayName("Should return email is duplicated when email is duplicated")
  @Test
  void shouldReturnUserSuccessfullyCreatedIfEverythingIsCorrect() throws Exception {

    Mockito.when(userRepository.save(Mockito.any())).thenReturn(User.builder().id(1).build());
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
            status().isCreated(),
            header().string("Location", "/users/1")
        );
  }

}