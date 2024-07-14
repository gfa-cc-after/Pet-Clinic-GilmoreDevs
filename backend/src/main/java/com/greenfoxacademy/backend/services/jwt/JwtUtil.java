package com.greenfoxacademy.backend.services.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

/**
 * This class is responsible for generating and validating JWT tokens.
 * We use the jjwt library to generate and validate tokens.
 * Find the library here: <a href="https://github.com/jwtk/jjwt">JJWT
 * documentation on github</a>
 */
@Component
public class JwtUtil {

  /**
   * The secret key used to generate the token.
   * TODO: Change this key to a more secure one.
   * This key should be stored in a secure location.
   * This should be at least 32 characters long.
   */
  private final String SECRET_KEY = "secretsecretsecretsecretsecretsecretsecretsecretsecretsecret";

  /**
   * Extracts the username from the token.
   *
   * @param token the token from which the username is extracted
   * @return the username
   */
  public String extractUsername(String token) {
    return extractClaim(token, Claims::getSubject);
  }

  /**
   * Extracts the expiration date from the token.
   *
   * @param token the token from which the expiration date is extracted
   * @return the expiration date
   */
  public Date extractExpiration(String token) {
    return extractClaim(token, Claims::getExpiration);
  }

  /**
   * Extracts a claim from the token.
   *
   * @param token          the token from which the claim is extracted
   * @param claimsResolver the function that extracts the claim
   * @return the claim
   */
  public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
    final Claims claims = extractAllClaims(token);
    return claimsResolver.apply(claims);
  }

  /**
   * Extracts all claims from the token.
   * Implemented based on:
   * <a href=
   * "https://github.com/jwtk/jjwt?tab=readme-ov-file#constant-parsing-key">
   * parse token documentation</a>
   *
   * @param token
   * @return all claims from the token
   */
  private Claims extractAllClaims(String token) {
    return Jwts.parser().verifyWith(getSigningKey()).build().parseSignedClaims(token).getPayload();
  }

  /**
   * Checks if the token is expired.
   *
   * @param token the token to be checked
   * @return true if the token is expired, false otherwise
   */
  private Boolean isTokenExpired(String token) {
    return extractExpiration(token).before(new Date());
  }

  /**
   * Generates a token based on the user details.
   *
   * @param userDetails the user details
   * @return the generated token
   */
  public String generateToken(UserDetails userDetails) {
    Map<String, Object> claims = new HashMap<>();
    return createToken(claims, userDetails.getUsername());
  }

  /**
   * Creates a token based on the claims and the subject.
   *
   * @param claims  the claims
   * @param subject the subject
   * @return the created token
   */
  // TODO: Change this to private when we have UserDetails-like objects
  public String createToken(Map<String, Object> claims, String subject) {
    Date now = new Date(System.currentTimeMillis());
    Date expiryDate = new Date(now.getTime() + 1000 * 60 * 60 * 10);
    return Jwts.builder()
        .claims(claims)
        .signWith(getSigningKey())
        .subject(subject)
        .issuedAt(now)
        .expiration(expiryDate)
        .compact();
  }

  /**
   * Validates the token based on the token and the user details.
   *
   * @param token       the token to be validated
   * @param userDetails the user details
   * @return true if the token is valid, false otherwise
   */
  public Boolean validateToken(String token, UserDetails userDetails) {
    final String username = extractUsername(token);
    return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
  }

  /**
   * This method generates a secret key based on the secret key string.
   * 
   * @return the secret key
   */
  private SecretKey getSigningKey() {
    byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
    return Keys.hmacShaKeyFor(keyBytes);
  }
}