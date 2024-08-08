package com.greenfoxacademy.backend.services.jwt;

import com.greenfoxacademy.backend.models.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class JwtServiceImpl {

  private final JwtEncoder encoder;

  public String generateToken(User user) {
    //@formatter:off
    Instant now = Instant.now();
    String scope = user.getAuthorities().stream()
            .map(GrantedAuthority::getAuthority)
            .collect(Collectors.joining(" "));
    JwtClaimsSet claims = JwtClaimsSet.builder()
            .issuer("self")
            .issuedAt(now)
            .expiresAt(now.plus(1, ChronoUnit.HOURS))
            .subject(user.getEmail())
            .claim("firstName", user.getFirstName())
            .claim("lastName", user.getLastName())
            .claim("scope", scope)
            .build();
    //@formatter:on
    return this.encoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();
  }

}
