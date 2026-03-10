package com.ventadirecta.pruebatecnica.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * Configuración separada para PasswordEncoder.
 * ¿Por qué en su propia clase? Para evitar dependencias circulares.
 * Si PasswordEncoder estuviera en SecurityConfig, se crearía un ciclo:
 *   SecurityConfig → JwtAuthenticationFilter → UserDetailsService → PasswordEncoder (en SecurityConfig)
 * Al ponerlo aquí, este bean se crea independientemente sin participar en el ciclo.
 */
@Configuration
public class PasswordEncoderConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}

