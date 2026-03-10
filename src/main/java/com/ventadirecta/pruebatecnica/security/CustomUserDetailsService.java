package com.ventadirecta.pruebatecnica.security;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * Implementa UserDetailsService, que es la interfaz que Spring Security
 * usa para buscar usuarios. Cuando el filtro JWT necesita validar un username,
 * llama a loadUserByUsername().
 */
@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final PasswordEncoder passwordEncoder;

    public CustomUserDetailsService(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    /**
     * Carga un usuario por su username.
     * PARA APRENDER: usamos un usuario hardcodeado "admin" con password "admin123".
     * EN PRODUCCIÓN: aquí consultarías tu tabla de usuarios en la BD.
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // Simular un usuario "admin" con contraseña "admin123"
        if ("admin".equals(username)) {
            return User.builder()
                    .username("admin")
                    .password(passwordEncoder.encode("admin123")) // Se encripta con BCrypt
                    .roles("ADMIN") // Le asignamos el rol ADMIN
                    .build();
        }
        throw new UsernameNotFoundException("Usuario no encontrado: " + username);
    }
}