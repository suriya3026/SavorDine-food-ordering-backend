
package com.savordine.backend.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    // Password Encoder
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // Security Configuration
    @Bean
    public SecurityFilterChain securityFilterChain(
            HttpSecurity http) throws Exception {

        http
            // Disable CSRF for REST API
            .csrf(csrf -> csrf.disable())

            // Disable CORS handling here
            .cors(cors -> cors.disable())

            // Allow API endpoints
            .authorizeHttpRequests(auth -> auth

                // User authentication
                .requestMatchers("/api/auth/**")
                .permitAll()

                // Food APIs
                .requestMatchers("/api/foods/**")
                .permitAll()

                // Category APIs
                .requestMatchers("/api/categories/**")
                .permitAll()

                // User APIs
                .requestMatchers("/api/users/**")
                .permitAll()

                // Cart APIs
                .requestMatchers("/api/cart/**")
                .permitAll()

                // Order APIs
                .requestMatchers("/api/orders/**")
                .permitAll()

                // Admin APIs including Admin Login
                .requestMatchers("/api/admin/**")
                .permitAll()

                // Other requests
                .anyRequest()
                .permitAll()
            );

        return http.build();
    }
}
