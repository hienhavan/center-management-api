package org.example.quanlytrungtam.auth;

import org.example.quanlytrungtam.config.jwt.JwtResponse;
import org.example.quanlytrungtam.config.jwt.JwtService;
import org.example.quanlytrungtam.user.User;
import org.example.quanlytrungtam.user.UserServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import org.springframework.web.bind.annotation.*;

import java.util.Optional;


@RestController
@CrossOrigin(origins = "*")
public class AuthController {
    @Autowired
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final UserServiceInterface userDetailsService;

    private final KafkaTemplate<String, Object> kafkaTemplate;


    @Autowired
    public AuthController(AuthenticationManager authenticationManager, JwtService jwtService, UserServiceInterface userDetailsService, KafkaTemplate<String, Object> kafkaTemplate) {
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
        this.userDetailsService = userDetailsService;
        this.kafkaTemplate = kafkaTemplate;
    }

    @PostMapping("/api/v1/login")
    public ResponseEntity<?> login(@RequestBody User user) {
        Optional<User> currentUser = userDetailsService.findByUserEmail(user.getEmail());

        if (!currentUser.isPresent()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("User not found");
        }

        if (!currentUser.get().getActive()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Locked account");
        }
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(user.getEmail(), user.getPassword())
            );
            SecurityContextHolder.getContext().setAuthentication(authentication);
            String jwt = jwtService.generateTokenLogin(authentication);
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            JwtResponse jwtResponse = new JwtResponse(currentUser.get().getId(), jwt, userDetails.getUsername(), currentUser.get().getFullName(), currentUser.get().getImg(), currentUser.get().getRoles().toString());
            kafkaTemplate.send("login-topic", jwtResponse);
            return ResponseEntity.ok(jwtResponse);
        } catch (AuthenticationException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Incorrect login information");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Lỗi: " + e.getMessage());
        }
    }

}


