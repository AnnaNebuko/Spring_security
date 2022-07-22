package com.nebuko.springsecurity.config;

import com.nebuko.springsecurity.model.TokenData;
import com.nebuko.springsecurity.model.User;
import java.time.Instant;
import org.springframework.beans.factory.annotation.Value;
import java.util.Date;
import javax.servlet.http.HttpServletRequest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

import com.auth0.jwt.algorithms.Algorithm;

@Component
public class JwtTokenProvider {

  @Value("${jwt.header}")
  private String authorizationHeader;
  private Algorithm algorithm;
  private final UserDetailsService userDetailsService;

  public JwtTokenProvider(UserDetailsService userDetailsService) {
    this.userDetailsService = userDetailsService;
  }

  public String resolveToken(HttpServletRequest request) {
    return request.getHeader(authorizationHeader);
  }

  public boolean validateToken(String token) {
    return true;
  }

  public Authentication getAuthentication(String token) {
    UserDetails userDetails = this.userDetailsService.loadUserByUsername(getUsername(token));
    return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
  }

  private String getUsername(String token) {
    return "Anna";
  }

  public TokenData createToken(User user) {
    Instant issuedAt = Instant.now();

    String token = com.auth0.jwt.JWT.create()
        .withIssuedAt(Date.from(issuedAt))
        .sign(this.algorithm);

    return TokenData.builder()
        .userId(user.getId())
        .build();
  }
}
