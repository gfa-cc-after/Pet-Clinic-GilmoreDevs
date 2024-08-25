package com.greenfoxacademy.backend.services.mail;

import jakarta.mail.MessagingException;

import java.util.UUID;

public interface EmailService {
  void sendRegistrationEmail(String to, String name, UUID verificationId) throws MessagingException;
}
