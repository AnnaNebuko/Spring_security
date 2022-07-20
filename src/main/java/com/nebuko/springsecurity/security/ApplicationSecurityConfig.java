package com.nebuko.springsecurity.security;

import java.util.ArrayList;
import java.util.List;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class ApplicationSecurityConfig {

  @Bean
  public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    http.authorizeHttpRequests()
        .antMatchers("/api/v1/developers").hasAuthority("ADMIN")
        .antMatchers("/api/v1/developers/{id}").permitAll()
        .and().formLogin();
    return http.build();
  }

  @Bean
  protected InMemoryUserDetailsManager configureAuthentication() {

    List<UserDetails> userDetails = new ArrayList<>();
    GrantedAuthority  employeeRole = new SimpleGrantedAuthority("EMPLOYEE");
    GrantedAuthority  adminRole = new SimpleGrantedAuthority("ADMIN");

    userDetails.add(new User("employee",
        "$2a$12$VJK7WvDp5.sA3I5on3GgTewjGHsuDDvjAR.1CmtZc57xO/bkh8yi6",
        List.of(employeeRole)));
    userDetails.add(new User("admin",
        "$2a$12$ikLxRl.h85CQHX7Az8KZIeO4tcUXNbCJEws4dCO3gB85uyP6fY9Lm",
        List.of(adminRole)));

    return new InMemoryUserDetailsManager(userDetails);
  }

  @Bean
  public PasswordEncoder getPasswordEncoder() {
    return new BCryptPasswordEncoder(12);
  }

}
