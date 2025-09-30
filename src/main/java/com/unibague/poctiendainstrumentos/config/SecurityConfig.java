package com.unibague.poctiendainstrumentos.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

/**
 * Configuración de seguridad para la aplicación usando Spring Security.
 *
 * <p>Esta clase define la configuración del filtro de seguridad HTTP, estableciendo las reglas de autorización
 * y autenticación para las rutas del servidor.</p>
 *
 * <p>La configuración deshabilita CSRF para facilitar el desarrollo y uso de APIs,
 * permite libre acceso a rutas bajo `/publico/**`, y requiere autenticación para cualquier otra solicitud.</p>
 *
 * <p>Utiliza autenticación HTTP básica (Basic Auth) para proteger las rutas y manejar credenciales.
 * La configuración está basada en la nueva versión de Spring Security que usa SecurityFilterChain.</p>
 *
 * @author Jorge
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig
{
    /**
     * Define la cadena de filtros de seguridad HTTP que aplican a las solicitudes.
     *
     * @param http el objeto HttpSecurity para configurar seguridad HTTP.
     * @return la cadena de filtros construida y configurada.
     * @throws Exception si ocurre un error durante la configuración.
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf().disable();

        http.authorizeHttpRequests(auth -> auth
                .requestMatchers(
                        "/publico/**"
                ).permitAll()
                .anyRequest().authenticated()
        );

        http.httpBasic();

        return http.build();
    }
}
