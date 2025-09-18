package com.example.PathFinder.util;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

import javax.crypto.SecretKey;
import java.util.Date;

public class JWTUtility {


    public static String generateToken(String username, SecretKey key) {
        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 36000000)) // 10 hours
                .signWith(key)
                .compact();
    }

}
