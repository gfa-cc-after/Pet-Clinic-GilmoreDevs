package com.greenfoxacademy.backend.services.auth;

import com.greenfoxacademy.backend.config.JWTConfig;
import com.greenfoxacademy.backend.models.User;
import java.time.Instant;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
class AuthServiceImpl implements AuthService {
  private final JwtEncoder jwtEncoder;
  private final JWTConfig jwtConfig;

  @Override
  public String generateToken(User user) {
    Instant now = Instant.now();

    long expiry = jwtConfig.getExpirationTime();
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