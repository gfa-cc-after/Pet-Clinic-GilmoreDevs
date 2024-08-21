package com.greenfoxacademy.backend.services.mail;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

@Component
public class EmailServiceImpl implements EmailService {
  @Autowired
  private JavaMailSender emailSender;

  public void sendRegistrationEmail(String to, String subject, String text, String imagePath) throws MessagingException {

    MimeMessage message = emailSender.createMimeMessage();
    MimeMessageHelper helper = new MimeMessageHelper(message, true);

    helper.setFrom("gilmoredevs.petclinic@gmail.com");
    helper.setTo(to);
    helper.setSubject(subject);
    helper.setText(text, true);

    // Add the image as an attachment
    ClassPathResource image = new ClassPathResource(imagePath);
    helper.addInline("image", image);

    emailSender.send(message);
  }
}

