package com.brotherselectronics.orderregistration.configs;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import static com.brotherselectronics.orderregistration.domains.enums.Role.ADMIN;
import static com.brotherselectronics.orderregistration.domains.enums.Role.USER;
import static org.springframework.http.HttpMethod.DELETE;
import static org.springframework.http.HttpMethod.GET;
import static org.springframework.http.HttpMethod.PATCH;
import static org.springframework.http.HttpMethod.POST;
import static org.springframework.http.HttpMethod.PUT;
import static org.springframework.security.crypto.factory.PasswordEncoderFactories.createDelegatingPasswordEncoder;

/**
 * Users are loaded by {@code MyUserDetailService}; Spring Boot wires it with {@link #passwordEncoder()}
 * into the authentication manager because both are beans.
 */
@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(AbstractHttpConfigurer::disable)
                .cors(Customizer.withDefaults())
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/home", "/").permitAll()
                        .requestMatchers(POST, "/users/login")
                        .hasAnyAuthority(USER.getAuthority(), ADMIN.getAuthority())
                        .requestMatchers(GET, "/**").hasAnyAuthority(USER.getAuthority(), ADMIN.getAuthority())
                        .requestMatchers(POST, "/**").hasAnyAuthority(ADMIN.getAuthority())
                        .requestMatchers(PUT, "/**").hasAnyAuthority(ADMIN.getAuthority())
                        .requestMatchers(PATCH, "/**").hasAnyAuthority(ADMIN.getAuthority())
                        .requestMatchers(DELETE, "/**").hasAnyAuthority(ADMIN.getAuthority())
                        .anyRequest().authenticated())
                .httpBasic(Customizer.withDefaults())
                .redirectToHttps(https -> https
                        .requestMatchers(request -> request.getHeader("X-Forwarded-Proto") != null))
                .build();
    }

    @Bean
    public static PasswordEncoder passwordEncoder() {
        return createDelegatingPasswordEncoder();
    }
}
