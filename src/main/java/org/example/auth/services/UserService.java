package org.example.auth.services;

import org.example.auth.dto.AuthResponse;
import org.example.auth.dto.AuthenticateRequest;
import org.example.auth.dto.CreateRequest;


public interface UserService {
    AuthResponse createUser(CreateRequest request);
    AuthResponse authenticateUser(AuthenticateRequest request);
}
