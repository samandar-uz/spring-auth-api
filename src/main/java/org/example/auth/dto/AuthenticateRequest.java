package org.example.auth.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AuthenticateRequest {
    private String usernameOrEmail;
    private String password;
}