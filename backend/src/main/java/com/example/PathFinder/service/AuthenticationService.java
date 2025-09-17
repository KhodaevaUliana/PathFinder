package com.example.PathFinder.service;

import com.example.PathFinder.model.LogInCredentials;
import com.example.PathFinder.util.JWTUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

@Service
public class AuthenticationService {
    private final UserDetailsManager userDetailsManager;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public AuthenticationService(UserDetailsManager userDetailsManager, PasswordEncoder passwordEncoder) {
        this.userDetailsManager = userDetailsManager;
        this.passwordEncoder = passwordEncoder;
    }

    //sign-up
    public void signUp (LogInCredentials logInCredentials) throws IllegalArgumentException {
        if (userDetailsManager.userExists(logInCredentials.getUsername())) {
            throw new IllegalArgumentException("User with this username already exists.");
        }
        if (logInCredentials.getUsername().length() > 50) {
            throw new IllegalArgumentException("The username is too long (> 50 characters).");
        }
        if (logInCredentials.getUsername().isEmpty()) {
            throw new IllegalArgumentException("Please enter a non-empty username.");
        }
        if (logInCredentials.getPassword().isEmpty()) {
            throw new IllegalArgumentException("Please enter a password.");
        }
        UserDetails newUser = User.withUsername(logInCredentials.getUsername())
                .password(passwordEncoder.encode(logInCredentials.getPassword()))
                .build();
        userDetailsManager.createUser(newUser);

        return;
    }

    //log-im
    public String logIn(LogInCredentials logInCredentials) throws IllegalArgumentException {
        if (!userDetailsManager.userExists(logInCredentials.getUsername())) {
            throw new IllegalArgumentException("Wrong password or username");
        }
        UserDetails user = userDetailsManager.loadUserByUsername(logInCredentials.getUsername());
        if (passwordEncoder.matches(logInCredentials.getPassword(), user.getPassword())) {
            return JWTUtility.generateToken(user.getUsername());
        } else {
            throw new IllegalArgumentException("Wrong password or username");
        }
    }

}
