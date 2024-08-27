package com.greenfoxacademy.backend.services.mail;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.greenfoxacademy.backend.config.EmailConfiguration;
import com.greenfoxacademy.backend.dtos.EmailSentDTO;
import com.greenfoxacademy.backend.services.user.UserService;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mail.javamail.JavaMailSender;

/**
 * Test class for {@link EmailServiceImpl}.
 * This class is responsible for testing the email sending functionality of the EmailServiceImpl class.
 * It uses Mockito for mocking dependencies and verifying interactions.
 */

@WebMvcTest
class EmailServiceImplTest {

  @MockBean
  private JavaMailSender emailSender;

  @MockBean
  private EmailConfiguration emailConfiguration;

  @MockBean
  private UserService userService;

  private EmailServiceImpl emailService;

  @BeforeEach
  void setUp() {
    emailSender = mock(JavaMailSender.class);
    userService = mock(UserService.class);
    emailService = new EmailServiceImpl(emailConfiguration, emailSender);

  }

  @DisplayName("When the verification email is successfully sent.")
  @Test
  void shouldSendEmail() throws MessagingException {
    String to = "example@gmail.com";
    String name = "Anna Example";
    UUID verificationID = UUID.randomUUID();
    MimeMessage message = mock(MimeMessage.class);

    when(emailSender.createMimeMessage()).thenReturn(message);
    // when(emailSender.send());
    emailService.sendRegistrationEmail(to, name, verificationID);

    Mockito.verify(emailSender, Mockito.times(1)).send(any(MimeMessage.class));
  }
}
