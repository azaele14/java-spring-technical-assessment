package com.ventadirecta.pruebatecnica.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

/**
 * OncePerRequestFilter garantiza que este filtro se ejecuta UNA SOLA VEZ por request.
 * (Hay casos donde un request puede pasar dos veces por los filtros, esto lo previene).
 */
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    @org.springframework.context.annotation.Lazy
    private UserDetailsService userDetailsService; // Lo crearemos en el paso 5

    /**
     * Este método se ejecuta en CADA petición HTTP.
     * Es el corazón de la autenticación JWT.
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {

        // 1. Obtener el header "Authorization"
        final String authHeader = request.getHeader("Authorization");

        String username = null;
        String jwt = null;

        // 2. Verificar que el header existe y empieza con "Bearer "
        //    El formato estándar es: "Bearer eyJhbGci..."
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            jwt = authHeader.substring(7); // Quitar "Bearer " (7 caracteres)
            username = jwtUtil.extractUsername(jwt); // Extraer el username del token
        }

        // 3. Si obtuvimos un username Y no hay autenticación previa en el contexto
        //    (SecurityContextHolder guarda la autenticación del request actual)
        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {

            // 4. Cargar los datos del usuario desde nuestro UserDetailsService
            UserDetails userDetails = userDetailsService.loadUserByUsername(username);

            // 5. Validar el token (firma correcta + no expirado + username coincide)
            if (jwtUtil.validateToken(jwt, userDetails.getUsername())) {

                // 6. Crear el objeto de autenticación de Spring Security
                UsernamePasswordAuthenticationToken authToken =
                        new UsernamePasswordAuthenticationToken(
                                userDetails,
                                null, // No necesitamos credenciales, ya validamos con JWT
                                userDetails.getAuthorities() // Los roles/permisos del usuario
                        );

                // 7. Agregar detalles del request (IP, session, etc.)
                authToken.setDetails(
                        new WebAuthenticationDetailsSource().buildDetails(request)
                );

                // 8. ¡CLAVE! Establecer la autenticación en el SecurityContext.
                //    A partir de aquí, Spring Security sabe que este request está autenticado.
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }

        // 9. Continuar con la cadena de filtros (eventualmente llega al controller)
        //    Si no se estableció autenticación, Spring Security rechazará el request.
        filterChain.doFilter(request, response);
    }
}