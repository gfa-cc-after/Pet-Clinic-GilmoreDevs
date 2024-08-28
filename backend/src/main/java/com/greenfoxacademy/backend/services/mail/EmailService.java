package com.greenfoxacademy.backend.services.mail;

import com.greenfoxacademy.backend.dtos.EmailSentDto;
import jakarta.mail.MessagingException;
import java.util.UUID;

/**
 * Service to handle emails.
 */
public interface EmailService {
  EmailSentDto sendRegistrationEmail(
      String to,
      String name,
      UUID verificationId) throws MessagingException;
}
