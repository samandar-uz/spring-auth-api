package org.example.auth.services;

import org.example.auth.dto.AuthResponse;
import org.example.auth.dto.LoginRequest;
import org.example.auth.dto.RegisterRequest;
import org.example.auth.entity.User;
import org.example.auth.repositories.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public AuthResponse register(RegisterRequest request) {
        // username/email bor-yo‘qligini tekshir
        if (userRepository.findByUsername(request.getUsername()).isPresent()) {
            return new AuthResponse("Username allaqachon band",null);
        }
        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            return new AuthResponse("Email allaqachon ro'yxatdan o'tgan",null);
        }

        User user = new User();
        user.setUsername(request.getUsername());
        user.setEmail(request.getEmail());
        user.setPasswordHash(passwordEncoder.encode(request.getPassword()));

        userRepository.save(user);

        return new AuthResponse("Foydalanuvchi muvaffaqiyatli ro'yxatdan o'tdi",user.getApiKey());
    }

    @Override
    public AuthResponse login(LoginRequest request) {
        var userOpt = userRepository.findByUsernameOrEmail(
                request.getUsernameOrEmail(),
                request.getUsernameOrEmail()
        );

        if (userOpt.isEmpty()) {
            return new AuthResponse("Foydalanuvchi topilmadi",null);
        }

        User user = userOpt.get();

        if (!passwordEncoder.matches(request.getPassword(), user.getPasswordHash())) {
            return new AuthResponse("Parol noto‘g‘ri",null);
        }

        // Keyin JWT qo‘shmoqchi bo‘lsang shu yerda token generatsiya qilasan
        return new AuthResponse("Kirish muvaffaqiyatli",user.getApiKey());
    }
}
