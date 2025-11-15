package org.example.auth.services;

import org.example.auth.dto.AuthResponse;
import org.example.auth.dto.AuthenticateRequest;
import org.example.auth.dto.CreateRequest;
import org.example.auth.entity.User;
import org.example.auth.repositories.UserRepository;
import org.example.auth.utils.ResponseBuilder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final JwtService jwtService;


    public UserServiceImpl(UserRepository userRepository, JwtService jwtService) {
        this.userRepository = userRepository;
        this.jwtService = jwtService;
    }

    @Override
    public AuthResponse createUser(CreateRequest request) {
        if (userRepository.findByUsername(request.getUsername()).isPresent()) {
            return new AuthResponse("Username is already taken", null, null);
        }
        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            return new AuthResponse("Email is already registered", null, null);
        }

        User user = new User();
        user.setUsername(request.getUsername());
        user.setEmail(request.getEmail());
        user.setKey(jwtService.generateToken(request.getUsername()));
        user.setPasswordHash(request.getPassword());

        userRepository.save(user);
        return AuthResponse.builder()
                .message("User created successfully")
                .user(ResponseBuilder.toDto(user))
                .token(user.getKey())
                .build();
    }

    @Override
    public AuthResponse authenticateUser(AuthenticateRequest request) {
        var userOpt = userRepository.findByUsernameOrEmail(request.getUsernameOrEmail(), request.getUsernameOrEmail());

        if (userOpt.isEmpty()) {
            return new AuthResponse("User not found", null, null);
        }

        User user = userOpt.get();

        if (!user.getPasswordHash().equals(request.getPassword())) {
            return new AuthResponse("Incorrect password", null, null);
        }

        return AuthResponse.builder().message("User successfully authenticated").user(ResponseBuilder.toDto(user)).token(user.getKey()).build();
    }
}