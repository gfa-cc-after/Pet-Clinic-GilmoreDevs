package com.greenfoxacademy.backend.config;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import lombok.Getter;
import org.springframework.context.annotation.Configuration;

/**
 * To create RSASecretKey class.
 */

@Getter
@Configuration
public class RSASecretKeys {

  private final RSAPublicKey publicKey;
  private final RSAPrivateKey privateKey;

  /**
   * To give RSASecretKeys function.
   */

  public RSASecretKeys() throws NoSuchAlgorithmException {
    KeyPairGenerator generator = KeyPairGenerator.getInstance("RSA");
    KeyPair keyPair = generator.generateKeyPair();
    this.publicKey = (RSAPublicKey) keyPair.getPublic();
    this.privateKey = (RSAPrivateKey) keyPair.getPrivate();
  }

}
