package com.greenfoxacademy.backend.config;

import com.greenfoxacademy.backend.models.User;
import com.greenfoxacademy.backend.services.UserService;
import com.greenfoxacademy.backend.services.jwt.JwtUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.ArrayList;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {
  private final JwtUtil jwtService;
  private final UserService userService;
  private final Logger logger = LoggerFactory.getLogger(JwtAuthenticationFilter.class);


  @Override
  protected void doFilterInternal(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull FilterChain filterChain) throws ServletException, IOException {
    final String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
    if (StringUtils.isEmpty(authHeader) || !StringUtils.startsWithIgnoreCase(authHeader, "Bearer ")) {
      filterChain.doFilter(request, response);
      return;
    }
    final String jwt = authHeader.substring(7);
    final String userEmail = jwtService.extractUsername(jwt);

    if (StringUtils.isEmpty(userEmail) && SecurityContextHolder.getContext().getAuthentication() == null) {
      User userDetails = (User) userService.loadUserByUsername(userEmail);
      if (jwtService.validateToken(jwt, userDetails)) {
//        SecurityContext context = SecurityContextHolder.createEmptyContext();
//        UsernamePasswordAuthenticationToken authToken = UsernamePasswordAuthenticationToken.authenticated(userDetails, null, userDetails.getAuthorities());
//        authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
//        context.setAuthentication(authToken);
//        SecurityContextHolder.setContext(context);
        Authentication authentication =
                new UsernamePasswordAuthenticationToken(userDetails.getUsername(), "", new ArrayList<>());
        SecurityContextHolder.getContext().setAuthentication(authentication);
      }
    }
    filterChain.doFilter(request, response);
  }

}