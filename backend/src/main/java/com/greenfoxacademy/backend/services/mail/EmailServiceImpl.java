package com.greenfoxacademy.backend.services.mail;

import com.greenfoxacademy.backend.config.EmailConfiguration;
import com.greenfoxacademy.backend.dtos.EmailSentDto;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

/**
 * EmailServiceImplementation to handle emails.
 */
@Service
@RequiredArgsConstructor
public class EmailServiceImpl implements EmailService {

  private final EmailConfiguration emailConfiguration;


  private final JavaMailSender emailSender;

  public EmailSentDto sendRegistrationEmail(String to, String name, UUID verificationId)
      throws MessagingException {

    MimeMessage message = emailSender.createMimeMessage();
    MimeMessageHelper helper = new MimeMessageHelper(message, true);
    final String imagePath = "assets/welcome_to_petclinic.png";
    final String senderEmail = "gilmoredevs.petclinic@gmail.com";
    final String subject = "Please verify your email for the Pet Clinic";

    final String verificationUrl =
      emailConfiguration.getBaseUrl() + "/verification?code=" + verificationId;
    String text = """
            <html>
            <body style='font-family: Arial, sans-serif;'>
            <div style='text-align: center;'>
            <h1 style='color: #333;'>Complete your registration!</h1>
            <div style='text-align: center>; margin: 20px 0;'>
            <p style='font-size: 16px; color: #555;'>Hello %s,</p>
            <p style='font-size: 16px; color: #555;'>
            Please verify your email by clicking on the following link:</p>
            <div style='text-align: center; margin: 20px 0;'>
            <a href='%s'
              style='
              background-color: #4CAF50;
              color: white;
              padding: 10px 20px;
              text-decoration: none;
              border-radius: 5px;'
              >Verify Email</a>
            </div>
            <div style='text-align: center; margin-top: 20px;'>
            <img src='cid:image' alt='Welcome to PetClinic' style='width: 400px; height: 400px;'>
            </div>
            </body>
            </html>""".formatted(name, verificationUrl);

    helper.setFrom(senderEmail);
    helper.setTo(to);
    helper.setSubject(subject);
    helper.setText(text, true);

    // Add the image as an attachment
    ClassPathResource image = new ClassPathResource(imagePath);
    helper.addInline("image", image);

    try {
      emailSender.send(message);
      return new EmailSentDto();
    } catch (MailException e) {
      throw new MessagingException("I have failed to send the email, cool, thank you");
    }
  }
}

