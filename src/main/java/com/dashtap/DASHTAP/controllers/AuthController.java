package com.dashtap.DASHTAP.controllers;

import com.dashtap.DASHTAP.payload.requests.LoginRequest;
import com.dashtap.DASHTAP.payload.requests.SignupRequest;
import com.dashtap.DASHTAP.services.impl.UserServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final UserServiceImpl userService;

    public AuthController(UserServiceImpl userService) {
        this.userService = userService;
    }

    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
        if(userService.existsByUsername(loginRequest.getUsername())) {
            return ResponseEntity.ok(userService.authenticate(loginRequest));
        }

        return new ResponseEntity<>("Incorrect login details", HttpStatus.UNAUTHORIZED);
    }

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signUpRequest) {
        if(userService.existsByUsername(signUpRequest.getUsername())) {
            return new ResponseEntity<>("Username already in use", HttpStatus.CONFLICT);
        }

        if(userService.existsByEmail(signUpRequest.getEmail())) {
            return new ResponseEntity<>("Email address already in use", HttpStatus.CONFLICT);
        }

        userService.register(signUpRequest);
        return new ResponseEntity<>("User added", HttpStatus.OK);
    }


}
