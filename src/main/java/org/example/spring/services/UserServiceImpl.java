package org.example.spring.services;

import org.example.spring.dto.AuthResponse;
import org.example.spring.dto.LoginRequest;
import org.example.spring.dto.RegisterRequest;
import org.example.spring.repositories.UserRepository;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository repo;
    private final BCryptPasswordEncoder encoder;
    private final com.example.auth.security.JwtService jwtService;

    public UserServiceImpl(UserRepository repo, BCryptPasswordEncoder encoder, com.example.auth.security.JwtService jwtServics) {
        this.repo = repo;
        this.encoder = encoder;
        this.jwtService = jwtService;
    }

    @Override
    public AuthResponse register(RegisterRequest req) {

        if (repo.findByUsername(req.getUsername()).isPresent())
            return new AuthResponse("Username band!", null);

        if (repo.findByEmail(req.getEmail()).isPresent())
            return new AuthResponse("Email band!", null);

        com.example.auth.entity.User u = new com.example.auth.entity.User();
        u.setUsername(req.getUsername());
        u.setEmail(req.getEmail());
        u.setPasswordHash(encoder.encode(req.getPassword()));

        repo.save(u);

        String token = jwtService.generateToken(u.getUsername());

        return new AuthResponse("Ro'yxatdan o'tdi", token);
    }

    @Override
    public AuthResponse login(LoginRequest req) {

        var userOpt = repo.findByUsernameOrEmail(req.getUsernameOrEmail(), req.getUsernameOrEmail());

        if (userOpt.isEmpty())
            return new AuthResponse("Foydalanuvchi topilmadi", null);

        com.example.auth.entity.User u = userOpt.get();

        if (!encoder.matches(req.getPassword(), u.getPasswordHash()))
            return new AuthResponse("Parol noto'g'ri", null);

        String token = jwtService.generateToken(u.getUsername());

        return new AuthResponse("Kirish muvaffaqiyatli", token);
    }
}
