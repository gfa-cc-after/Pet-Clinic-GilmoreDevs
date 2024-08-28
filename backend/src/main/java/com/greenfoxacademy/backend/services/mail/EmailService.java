package com.greenfoxacademy.backend.services.mail;

import com.greenfoxacademy.backend.dtos.EmailSentDto;
import java.util.UUID;
import jakarta.mail.MessagingException;

/**
 * Service to handle emails.
 */
public interface EmailService {
  EmailSentDto sendRegistrationEmail(String to, String name, UUID verificationId) throws MessagingException;
}
