package com.greenfoxacademy.backend.services.mail;

import com.greenfoxacademy.backend.dtos.EmailSentDTO;
import jakarta.mail.MessagingException;

import java.util.UUID;

public interface EmailService {
  EmailSentDTO sendRegistrationEmail(String to, String name, UUID verificationId) throws MessagingException;
}
