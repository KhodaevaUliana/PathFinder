package com.example.PathFinder.service;

import com.example.PathFinder.model.LogInCredentials;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.platform.commons.util.StringUtils;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.UserDetailsManager;

import javax.crypto.SecretKey;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class AuthenticationServiceTest {
    @Mock
    private UserDetailsManager userDetailsManager;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private SecretKey jwtSigningkey;

    private AuthenticationService authenticationService;

    @BeforeEach
    public void setUp() {
        authenticationService = new AuthenticationService(userDetailsManager, passwordEncoder, jwtSigningkey);
    }

    @Test
    public void signUpUserAlreadyExists() {
        LogInCredentials credentials = new LogInCredentials();
        credentials.setUsername("Ivan");
        credentials.setPassword("SecurePassword");

        //mock repository
        when(userDetailsManager.userExists("Ivan")).thenReturn(true);

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            authenticationService.signUp(credentials);
        });
    }

    @Test
    public void signUpTooLongUsername() {
        LogInCredentials credentials = new LogInCredentials();
        credentials.setUsername("A".repeat(51)); //50 is a limit
        credentials.setPassword("SecurePassword");

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            authenticationService.signUp(credentials);
        });
    }

    @Test
    public void signUpEmptyUsername() {
        LogInCredentials credentials = new LogInCredentials();
        credentials.setUsername("");
        credentials.setPassword("SecurePassword");

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            authenticationService.signUp(credentials);
        });
    }

    @Test
    public void signUpEmptyPassword() {
        LogInCredentials credentials = new LogInCredentials();
        credentials.setUsername("Ivan");
        credentials.setPassword("");

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            authenticationService.signUp(credentials);
        });
    }

    @Test
    public void logInUsernameDoesNotExist() {
        LogInCredentials credentials = new LogInCredentials();
        credentials.setUsername("Ivan");
        credentials.setPassword("SecurePassword");

        //mock repository
        when(userDetailsManager.userExists("Ivan")).thenReturn(false);

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            authenticationService.logIn(credentials);
        });
    }

    @Test
    public void logInWrongPassword() {
        LogInCredentials credentials = new LogInCredentials();
        credentials.setUsername("Ivan");
        credentials.setPassword("SecurePassword");

        UserDetails user = User.withUsername("Ivan").password("Qwerty").build();

        //mock repository
        when(userDetailsManager.userExists("Ivan")).thenReturn(true);
        //mock user details manager
        when(userDetailsManager.loadUserByUsername("Ivan")).thenReturn(user);
        //mock passwordencoder
        when(passwordEncoder.matches( "SecurePassword", "Qwerty")).thenReturn(false);

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            authenticationService.logIn(credentials);
        });
    }

    public void deleteUserWhenUserDoesNotExist() {
        String username = "Ivan";

        //mock repository
        when(userDetailsManager.userExists("Ivan")).thenReturn(false);

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            authenticationService.deleteUser(username);
        });
    }

    public void deleteUserWhenExists() {
        String username = "Ivan";

        //mock repository
        when(userDetailsManager.userExists("Ivan")).thenReturn(true);

        //make sure that userDetailsManager indeed calls delete
        verify(userDetailsManager).deleteUser(username);
    }


}
