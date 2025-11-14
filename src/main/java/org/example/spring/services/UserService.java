package org.example.spring.services;

import org.example.spring.dto.AuthResponse;
import org.example.spring.dto.LoginRequest;
import org.example.spring.dto.RegisterRequest;

public interface UserService {
    AuthResponse register(RegisterRequest request);
    AuthResponse login(LoginRequest request);
}
