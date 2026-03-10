package com.ventadirecta.pruebatecnica.controller;

import com.ventadirecta.pruebatecnica.security.JwtUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/v1/auth")
@Tag(name = "Authentication", description = "Endpoints for authentication")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping("/login")
    @Operation(summary = "Login", description = "Authenticates user and returns JWT token")
    public ResponseEntity<Map<String, String>> login(@RequestBody Map<String, String> credentials) {
        // 1. Extraer username y password del body
        String username = credentials.get("username");
        String password = credentials.get("password");

        // 2. AuthenticationManager verifica las credenciales
        //    Internamente llama a CustomUserDetailsService.loadUserByUsername()
        //    y compara los passwords con BCrypt.
        //    Si falla, lanza BadCredentialsException → respuesta 401.
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(username, password)
        );

        // 3. Si llegamos aquí, las credenciales son correctas.
        //    Generamos el token JWT.
        String token = jwtUtil.generateToken(username);

        // 4. Devolvemos el token al cliente
        return ResponseEntity.ok(Map.of("token", token));
    }
}