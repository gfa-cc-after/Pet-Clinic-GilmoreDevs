package com.greenfoxacademy.backend.config;

import com.greenfoxacademy.backend.models.User;
import com.greenfoxacademy.backend.services.user.UserService;
import com.nimbusds.jose.jwk.JWK;
import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.source.ImmutableJWKSet;
import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.proc.SecurityContext;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.oauth2.jwt.*;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.oauth2.server.resource.web.BearerTokenAuthenticationEntryPoint;
import org.springframework.security.oauth2.server.resource.web.access.BearerTokenAccessDeniedHandler;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;


/**
 * To give security permission to all endpoints.
 */
@Configuration
@EnableWebSecurity
@EnableMethodSecurity(securedEnabled = true)
@RequiredArgsConstructor
public class SecurityConfig {

  private final JwtDecoder jwtDecoder;

  private final String[] ALLOWED_URLS = {"/register", "/login", "/health-check", "/swagger-ui", "/v3/api-docs"};

  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    // @formatter:off
      http
              .authorizeHttpRequests((authorize) -> authorize
                      .requestMatchers(ALLOWED_URLS).permitAll()
                      .requestMatchers("/profile-update").authenticated()
                      .anyRequest().authenticated()
              )
              .csrf((csrf) -> csrf.ignoringRequestMatchers("/login", "/register"))
              .httpBasic(Customizer.withDefaults())
              .oauth2ResourceServer((oauth2) -> oauth2
                      .jwt((jwt) -> jwt.decoder(jwtDecoder))
              )
              .sessionManagement((session) -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))

              .exceptionHandling((exceptions) -> exceptions
                      .authenticationEntryPoint(new BearerTokenAuthenticationEntryPoint())
                      .accessDeniedHandler(new BearerTokenAccessDeniedHandler())
              );
      // @formatter:on
    return http.build();
  }


  @Bean
  public CorsConfigurationSource corsConfigurationSource() {
    CorsConfiguration configuration = new CorsConfiguration();
    configuration.setAllowedOrigins(List.of("http://localhost:5173"));
    configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS", "HEAD"));
    configuration.setAllowedHeaders(Arrays.asList("*"));
    configuration.setAllowCredentials(true);

    UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
    source.registerCorsConfiguration("/**", configuration);
    return source;
  }
}

