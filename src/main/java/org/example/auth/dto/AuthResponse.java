package org.example.auth.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AuthResponse {
    private String message;
    private User user;
    private String token;

    public AuthResponse(String message, User user, String token) {
        this.message = message;
        this.user = user;
        this.token = token;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class User {
        private Integer id;
        private String username;
        private String email;
        private String roles;
    }
}
