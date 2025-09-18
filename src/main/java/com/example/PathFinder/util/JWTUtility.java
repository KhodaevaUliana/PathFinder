package com.example.PathFinder.util;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

import javax.crypto.SecretKey;
import java.util.Date;

public class JWTUtility {

    private static final SecretKey key = Keys.secretKeyFor(SignatureAlgorithm.HS256);

    public static String generateToken(String username) {
        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 36000000)) // 10 hours
                .signWith(key)
                .compact();
    }

    public static SecretKey getKey() {
        return key;
    }

}
