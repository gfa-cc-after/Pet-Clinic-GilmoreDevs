package com.greenfoxacademy.backend.services.auth;

import com.greenfoxacademy.backend.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.stream.Collectors;

@Service
class AuthServiceImpl implements AuthService {

  @Autowired
  private JwtEncoder jwtEncoder;

  @Override
  public String generateToken(User user) {
    Instant now = Instant.now();

    long expiry = 36000L;
    // @formatter:off
    String scope = user.getAuthorities().stream()
            .map(GrantedAuthority::getAuthority)
            .collect(Collectors.joining(" "));
    JwtClaimsSet claims = JwtClaimsSet.builder()
            .issuer("self")
            .issuedAt(now)
            .expiresAt(now.plusSeconds(expiry))
            .subject(user.getEmail())
            .claim("firstName", user.getFirstName())
            .claim("lastName", user.getLastName())
            .claim("scope", scope)
            .build();
    // @formatter:on
    return this.jwtEncoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();
  }
}