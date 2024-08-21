package com.greenfoxacademy.backend;

import com.greenfoxacademy.backend.services.mail.EmailServiceImpl;
import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;


@SpringBootApplication
public class EmailApplicationExample {
  @Autowired
  private EmailServiceImpl senderService;


  public static void main(String[] args) {
    SpringApplication.run(EmailApplicationExample.class, args);
  }

  @EventListener(ApplicationReadyEvent.class)
  public void sendEmail() throws MessagingException {
    String htmlContent = "<html>" +
            "<body style='font-family: Arial, sans-serif;'>" +
            "<div style='text-align: center;'>" +
            "<h1 style='color: #333;'>Complete your registration!</h1>" +
            "<div style='text-align: center>; margin: 20px 0;'>" +
            "<p style='font-size: 16px; color: #555;'>Hello there,</p>" +
            "<p style='font-size: 16px; color: #555;'>Please verify your email by clicking on the following link:</p>" +
            "<div style='text-align: center; margin: 20px 0;'>" +
            "<a href='https://frontend-production-e146.up.railway.app/' style='background-color: #4CAF50; color: white; padding: 10px 20px; text-decoration: none; border-radius: 5px;'>Verify Email</a>" +
            "</div>" +
            "<div style='text-align: center; margin-top: 20px;'>" +
            "<img src='cid:image' alt='Welcome to PetClinic' style='width: 400px; height: 400px;'>" +
            "</div>" +
            "</body>" +
            "</html>";

    senderService.sendRegistrationEmail("carlosponton20@gmail.com", "Welcome to PetClinic", htmlContent,
    "assets/welcome_to_petclinic.png");
  }

}
