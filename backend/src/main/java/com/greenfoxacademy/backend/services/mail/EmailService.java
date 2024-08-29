package com.greenfoxacademy.backend.services.mail;

import com.greenfoxacademy.backend.dtos.EmailSentDto;
import jakarta.mail.MessagingException;
import java.util.UUID;

/**
 * An interface for a service that handles sending emails.
 */
public interface EmailService {
  EmailSentDto sendRegistrationEmail(
      String to,
      String name,
      UUID verificationId) throws MessagingException;
}
