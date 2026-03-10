package com.ventadirecta.pruebatecnica.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.function.Function;

@Component // Para que Spring lo maneje como bean y puedas inyectarlo
public class JwtUtil {

    @Value("${jwt.secret}")
    private String secret; // La clave secreta del application.properties

    @Value("${jwt.expiration}")
    private long expiration; // Tiempo de expiración en ms

    /**
     * Genera la clave criptográfica a partir del string secreto.
     * ¿Por qué? JJWT necesita un objeto SecretKey, no un String.
     * Keys.hmacShaKeyFor() crea una clave compatible con HMAC-SHA256.
     */
    private SecretKey getSigningKey() {
        return Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
    }

    /**
     * GENERA un nuevo token JWT.
     * - subject: generalmente el username del usuario autenticado.
     * - issuedAt: fecha de creación del token.
     * - expiration: fecha de expiración (ahora + milisegundos configurados).
     * - signWith: firma el token con nuestra clave secreta.
     * - compact(): serializa todo a un String (el token final).
     */
    public String generateToken(String username) {
        return Jwts.builder()
                .subject(username)
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(getSigningKey())
                .compact();
    }

    /**
     * Extrae el username (subject) del token.
     * ¿Cómo? Parsea el token, verifica la firma, y lee el campo "sub" del payload.
     */
    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    /**
     * Extrae la fecha de expiración del token.
     */
    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    /**
     * Método genérico para extraer cualquier claim del token.
     * Usa un Function<Claims, T> para ser flexible.
     */
    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    /**
     * Parsea y valida el token completo.
     * Si la firma no coincide o el token está malformado, lanza excepción.
     */
    private Claims extractAllClaims(String token) {
        return Jwts.parser()
                .verifyWith(getSigningKey()) // Verifica con nuestra clave
                .build()
                .parseSignedClaims(token)    // Parsea el token firmado
                .getPayload();               // Obtiene el payload (claims)
    }

    /**
     * Verifica si el token ya expiró.
     * Compara la fecha de expiración con la fecha actual.
     */
    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    /**
     * Valida el token completo:
     * 1. ¿El username del token coincide con el username esperado?
     * 2. ¿El token NO ha expirado?
     */
    public boolean validateToken(String token, String username) {
        final String tokenUsername = extractUsername(token);
        return (tokenUsername.equals(username) && !isTokenExpired(token));
    }
}