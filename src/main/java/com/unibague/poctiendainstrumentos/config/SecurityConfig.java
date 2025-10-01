package com.unibague.poctiendainstrumentos.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable()) // Deshabilitar CSRF para facilitar APIs

                .cors()  // Habilitar configuración CORS definidas externamente

                .and()
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()    // Permitir sin autenticación OPTIONS (preflight)
                        .requestMatchers("/publico/**").permitAll()                // Permitir sin auth rutas públicas
                        .requestMatchers("/actuator/**").permitAll()               // Permitir sin auth healthcheck actuator
                        .requestMatchers("/instrumentos/**").permitAll()           // Permitir sin auth acceso a instrumentos (POST/GET)
                        .anyRequest().authenticated()                              // El resto requiere autenticación
                );

        return http.build();
    }
}
