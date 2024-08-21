package com.greenfoxacademy.backend.services.mail;

import jakarta.mail.MessagingException;

public interface EmailService {
  void sendRegistrationEmail(String to, String subject, String text, String imagePath) throws MessagingException;
}
