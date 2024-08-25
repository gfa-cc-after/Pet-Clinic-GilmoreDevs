package com.greenfoxacademy.backend;

import com.greenfoxacademy.backend.services.mail.EmailServiceImpl;
import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;

import java.util.UUID;


@SpringBootApplication
public class EmailApplicationExample {
  @Autowired
  private EmailServiceImpl senderService;


  public static void main(String[] args) {
    SpringApplication.run(EmailApplicationExample.class, args);
  }

  @EventListener(ApplicationReadyEvent.class)
  public void sendEmail() throws MessagingException {
    // mark.kovari@ext.codecool.com
    // kovarimarkofficial@gmail.com
    senderService.sendRegistrationEmail("kovarimarkofficial@gmail.com", "Jozsef", UUID.randomUUID());
  }

}
