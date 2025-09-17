package com.example.PathFinder.controller;

import com.example.PathFinder.service.AuthenticationService;
import com.example.PathFinder.util.JWTUtility;
import com.example.PathFinder.model.LogInCredentials;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/auth")
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    @Autowired
    public AuthenticationController (AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }


    //sign-up -- create a user
    @PostMapping("/signup")
    public ResponseEntity<String> signUp (@RequestBody LogInCredentials logInCredentials) {
        try {
            authenticationService.signUp(logInCredentials);
        } catch (IllegalArgumentException error) {
            return ResponseEntity.badRequest().body(error.getMessage());
        } catch (Exception error) {
            return ResponseEntity.internalServerError().body(error.getMessage());
        }
        return ResponseEntity.ok("User created successfully.");
    }

    //log in -- get a JWT token!
    @PostMapping("/login")
    public ResponseEntity<String> logIn (@RequestBody LogInCredentials logInCredentials) {
        try {
            String token = authenticationService.logIn(logInCredentials);
            return ResponseEntity.ok(token);
        } catch (IllegalArgumentException error) {
            return ResponseEntity.badRequest().body(error.getMessage());
        } catch (Exception error) {
            return ResponseEntity.internalServerError().body(error.getMessage());
        }
    }

    //delete a user
    @DeleteMapping("/delete")
    public ResponseEntity<String> deleteUser(Authentication authentication) {
        String username = authentication.getName();
        try {
            authenticationService.deleteUser(username);
            return ResponseEntity.ok().body("User successfully deleted");
        } catch (Exception error) {
            return ResponseEntity.internalServerError().body(error.getMessage());
        }
    }



}
