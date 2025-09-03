package com.example.PathFinder;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLOutput;

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
        UserDetails newUser = User.withUsername(logInCredentials.getUsername())
                .password(passwordEncoder.encode(logInCredentials.getPassword()))
                .build();
        userDetailsManager.createUser(newUser);

        return ResponseEntity.ok("User created successfully.");
    }

    @GetMapping("/hello")
    public String hello() {
        return "Hello";
    }


}
