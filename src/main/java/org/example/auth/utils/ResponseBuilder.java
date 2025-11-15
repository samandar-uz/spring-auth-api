package org.example.auth.utils;

import lombok.experimental.UtilityClass;
import org.example.auth.dto.AuthResponse;
import org.example.auth.entity.User;

@UtilityClass
public final class ResponseBuilder {
    public static AuthResponse.User toDto(User user) {
        return AuthResponse.User.builder()
                .id(user.getId())
                .email(user.getEmail())
                .username(user.getUsername())
                .roles(user.getRole())
                .build();


    }
}
