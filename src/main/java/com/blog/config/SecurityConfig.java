package com.blog.config;

import com.blog.domain.entities.User;
import com.blog.repositories.UserRepository;
import com.blog.security.BlogUserDetailsService;
import com.blog.security.JwtAuthenticationFilter;
import com.blog.services.AuthenticationService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class SecurityConfig {

  @Bean
  public JwtAuthenticationFilter jwtAuthenticationFilter(
      AuthenticationService authenticationService) {
    return new JwtAuthenticationFilter(authenticationService);
  }

  @Bean
  public UserDetailsService userDetailsService(UserRepository userRepository) {
    BlogUserDetailsService blogUserDetailsService = new BlogUserDetailsService(userRepository);

    String email = "user@test.com";
    userRepository.findByEmail(email).orElseGet(()->{
      User user = User.builder()
          .name("Test User")
          .email(email)
          .password(passwordEncoder().encode("password"))
          .build();
      return userRepository.save(user);
    });

    return blogUserDetailsService;
  }

  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity http,
      JwtAuthenticationFilter jwtAuthenticationFilter) throws Exception {
    http.authorizeHttpRequests(auth -> auth
            .requestMatchers(HttpMethod.POST, "/api/v1/auth/login/**").permitAll()
            .requestMatchers(HttpMethod.GET, "/api/v1/categories/**").permitAll()
            .requestMatchers(HttpMethod.GET, "/api/v1/posts/drafts").authenticated()
            .requestMatchers(HttpMethod.GET, "/api/v1/posts/**").permitAll()
            .requestMatchers(HttpMethod.GET, "/api/v1/tags/**").permitAll()
            .anyRequest().authenticated()
        )
        .csrf(AbstractHttpConfigurer::disable)
        .sessionManagement(httpSecuritySessionManagementConfigurer ->
            httpSecuritySessionManagementConfigurer.sessionCreationPolicy(
                SessionCreationPolicy.STATELESS))
        .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

    return http.build();
  }

  @Bean
  public PasswordEncoder passwordEncoder() {
    return PasswordEncoderFactories.createDelegatingPasswordEncoder();
  }

  @Bean
  AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
    return config.getAuthenticationManager();
  }

}
