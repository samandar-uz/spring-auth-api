package org.example.auth.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateRequest {
    private String username;
    private String email;
    private String password;
}