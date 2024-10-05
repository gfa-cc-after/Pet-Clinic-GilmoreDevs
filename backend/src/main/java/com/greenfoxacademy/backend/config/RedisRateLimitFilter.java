package com.greenfoxacademy.backend.config;

import com.greenfoxacademy.backend.services.auth.RateLimiterService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.hibernate.annotations.Comment;
import org.hibernate.annotations.Filter;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

@Component
@Filter(name = "redis-filter")
@RequiredArgsConstructor
public class RedisRateLimitFilter extends OncePerRequestFilter {
  private final RateLimiterService rateLimiterService;

  @Override
  protected void doFilterInternal(HttpServletRequest request,
                                  HttpServletResponse response,
                                  FilterChain filterChain) throws ServletException, IOException {
     if (SecurityContextHolder.getContext().getAuthentication() == null){
        filterChain.doFilter(request, response);
        return;
      }
      UserDetails userDetails = (UserDetails) SecurityContextHolder
              .getContext()
              .getAuthentication()
              .getDetails();
      if (userDetails != null) {
        List<String> userAuthorities = userDetails
                .getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .toList();
        boolean notAllowed = rateLimiterService.isRateLimited(userDetails.getUsername(), userAuthorities);
        if (notAllowed) {
          response.sendError(HttpStatus.TOO_MANY_REQUESTS.value(),
                  "Too many request, please return later!");
        }
    }
    filterChain.doFilter(request, response);
  }
}
