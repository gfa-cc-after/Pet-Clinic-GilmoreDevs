package com.greenfoxacademy.backend.utils;

import com.greenfoxacademy.backend.models.User;
import org.springframework.security.test.context.support.WithSecurityContextFactory;


import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.WithSecurityContextFactory;

/**
 * @author Rob Winch
 */
public class WithMockCustomUserSecurityContextFactory implements WithSecurityContextFactory<WithMockCustomUser> {

  @Override
  public SecurityContext createSecurityContext(WithMockCustomUser customUser) {
    SecurityContext context = SecurityContextHolder.createEmptyContext();
    User principal = User.builder()
            .email(customUser.username())
            .firstName(customUser.name())
            .build();
    Authentication auth = UsernamePasswordAuthenticationToken.authenticated(principal, "password",
            principal.getAuthorities());
    context.setAuthentication(auth);
    return context;
  }

}