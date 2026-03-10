package com.ventadirecta.pruebatecnica.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration      // Indica que esta clase tiene configuración de beans
@EnableWebSecurity  // Activa la configuración personalizada de Spring Security
public class SecurityConfig {

    @Autowired
    private JwtAuthenticationFilter jwtAuthenticationFilter;


    /**
     * AuthenticationManager: Spring Security lo usa internamente para
     * autenticar usuarios (verificar username + password).
     * Lo necesitamos en el AuthController para el proceso de login.
     */
    @Bean
    public AuthenticationManager authenticationManager(
            AuthenticationConfiguration authConfig) throws Exception {
        return authConfig.getAuthenticationManager();
    }

    /**
     * ¡LA CONFIGURACIÓN PRINCIPAL!
     * Define toda la política de seguridad de tu aplicación.
     */
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                // 1. Desactivar CSRF (Cross-Site Request Forgery)
                //    ¿Por qué? CSRF protege contra ataques en aplicaciones con sesiones/cookies.
                //    Como usamos JWT (stateless), no necesitamos CSRF.
                .csrf(csrf -> csrf.disable())

                // 2. Definir las reglas de autorización para cada ruta
                .authorizeHttpRequests(auth -> auth
                        // Rutas PÚBLICAS (sin token):
                        .requestMatchers("/api/v1/auth/**").permitAll()  // Login y registro

                        // Swagger UI y OpenAPI docs - PÚBLICO para que no se afecte tu interfaz
                        .requestMatchers(
                                "/swagger-ui/**",
                                "/swagger-ui.html",
                                "/v3/api-docs/**",
                                "/swagger-resources/**",
                                "/webjars/**"
                        ).permitAll()

                        // TODO lo demás requiere autenticación (tus endpoints de API)
                        .anyRequest().authenticated()
                )

                // 3. Política de sesiones: STATELESS
                //    ¿Por qué? No queremos que Spring cree sesiones HTTP.
                //    Cada request se autentica independientemente con su JWT.
                .sessionManagement(session ->
                        session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )

                // 4. Agregar nuestro filtro JWT ANTES del filtro estándar de Spring
                //    Esto hace que nuestro filtro se ejecute primero y establezca
                //    la autenticación antes de que Spring Security la verifique.
                .addFilterBefore(jwtAuthenticationFilter,
                        UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}