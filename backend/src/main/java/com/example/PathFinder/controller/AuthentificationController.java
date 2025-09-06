package com.example.PathFinder.controller;

import com.example.PathFinder.JWTUtility;
import com.example.PathFinder.LogInCredentials;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.web.bind.annotation.*;
import io.jsonwebtoken.*;


@RestController
@RequestMapping("/auth")
public class AuthentificationController {

    @Autowired
    private UserDetailsManager userDetailsManager;


    @Autowired
    private PasswordEncoder passwordEncoder;




    //sign-up -- create a user
    @PostMapping("/signup")
    public ResponseEntity<String> signUp (@RequestBody LogInCredentials logInCredentials) {
        if (userDetailsManager.userExists(logInCredentials.getUsername())) {
            return ResponseEntity.badRequest().body("User with this username already exists.");
        }
        if (logInCredentials.getUsername().isEmpty()) {
            return ResponseEntity.badRequest().body("Please enter a non-empty username.");
        }
        if (logInCredentials.getPassword().isEmpty()) {
            return ResponseEntity.badRequest().body("Please enter a password.");
        }
        UserDetails newUser = User.withUsername(logInCredentials.getUsername())
                .password(passwordEncoder.encode(logInCredentials.getPassword()))
                .build();
        userDetailsManager.createUser(newUser);

        return ResponseEntity.ok("User created successfully.");
    }

    //log in -- get a JWT token!
    @PostMapping("/login")
    public ResponseEntity<String> logIn (@RequestBody LogInCredentials logInCredentials) {
        if (!userDetailsManager.userExists(logInCredentials.getUsername())) {
            return ResponseEntity.badRequest().body("User doesn't exist.");
        }
        UserDetails user = userDetailsManager.loadUserByUsername(logInCredentials.getUsername());
        if (passwordEncoder.matches(logInCredentials.getPassword(), user.getPassword())) {
            String token = JWTUtility.generateToken(user.getUsername());
            return ResponseEntity.ok(token);
        } else {
            return ResponseEntity.badRequest().body("Wrong password");
        }
    }


}
