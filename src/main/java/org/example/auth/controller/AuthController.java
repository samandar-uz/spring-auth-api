package org.example.auth.controller;

import org.example.auth.dto.AuthResponse;

import org.example.auth.dto.AuthenticateRequest;
import org.example.auth.dto.CreateRequest;
import org.example.auth.services.UserService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private final UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/sign-up")
    public AuthResponse createUser(@RequestBody CreateRequest request) {
        return userService.createUser(request);
    }

    @PostMapping("/sign-in")
    public AuthResponse authenticateUser(@RequestBody AuthenticateRequest request) {
        return userService.authenticateUser(request);
    }
}
