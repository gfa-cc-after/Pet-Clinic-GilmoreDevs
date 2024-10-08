package com.greenfoxacademy.backend.services.mail;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import com.greenfoxacademy.backend.config.EmailConfiguration;
import com.greenfoxacademy.backend.services.pet.PetService;
import com.greenfoxacademy.backend.services.user.OwnerService;
import com.greenfoxacademy.backend.services.user.vet.VetService;
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
 * This class is responsible for testing the email sending functionality of
 * the EmailServiceImpl class.
 * It uses Mockito for mocking dependencies and verifying interactions.
 */

@WebMvcTest
class EmailServiceImplTest {

  @MockBean
  private JavaMailSender emailSender;

  @MockBean
  private EmailConfiguration emailConfiguration;

  @MockBean
  private OwnerService ownerService;

  @MockBean
  private PetService petService;

  @MockBean
  private VetService vetService;

  private EmailServiceImpl emailService;

  @BeforeEach
  void setUp() {
    emailSender = mock(JavaMailSender.class);
    ownerService = mock(OwnerService.class);
    emailService = new EmailServiceImpl(emailConfiguration, emailSender);

  }

  @DisplayName("When the verification email is successfully sent.")
  @Test
  void shouldSendEmail() throws MessagingException {
    String to = "example@gmail.com";
    String name = "Anna Example";
    UUID verificationId = UUID.randomUUID();
    MimeMessage message = mock(MimeMessage.class);

    when(emailSender.createMimeMessage()).thenReturn(message);
    // when(emailSender.send());
    emailService.sendRegistrationEmail(to, name, verificationId);

    Mockito.verify(emailSender, Mockito.times(1)).send(any(MimeMessage.class));
  }
}
