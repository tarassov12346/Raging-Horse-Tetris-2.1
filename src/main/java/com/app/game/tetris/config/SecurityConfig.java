package com.app.game.tetris.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import static org.asynchttpclient.util.HttpConstants.Methods.OPTIONS;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }

    @Bean
    @Order(1)
    public SecurityFilterChain configurePublicEndpoints(HttpSecurity http) throws Exception {
        http.securityMatcher(OPTIONS, "/register","/6")
                .authorizeHttpRequests((requests) -> requests
                        .anyRequest().permitAll() // allow CORS option calls for Swagger UI
                );
        return http.build();
    }

    @Bean
    @Order(2)
    public SecurityFilterChain configure(HttpSecurity http) throws Exception {
        http.securityMatcher("/hello", "/admin", "/admin/**")

                .authorizeHttpRequests((requests) -> requests.anyRequest().authenticated())
                .httpBasic(Customizer.withDefaults());
        return http.build();
    }
}
