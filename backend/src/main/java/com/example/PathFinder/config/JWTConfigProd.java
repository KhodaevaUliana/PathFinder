package com.example.PathFinder.config;

import io.jsonwebtoken.security.Keys;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;

import javax.crypto.SecretKey;
import java.util.Base64;

@Configuration
@Profile("!dev")
public class JWTConfigProd {

    @Bean
    public SecretKey jwtSigningKey() {
        String base64Key = System.getenv("JWT_SECRET");
        if (base64Key == null || base64Key.isBlank()) {
            throw new IllegalStateException("JWT_SECRET env variable is not set!");
        }
        byte[] keyBytes = Base64.getDecoder().decode(base64Key);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    @Bean
    public JwtDecoder jwtDecoder(SecretKey jwtSigningKey) {
        return NimbusJwtDecoder.withSecretKey(jwtSigningKey).build();
    }
}
