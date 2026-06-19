package com.example.auth_service.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;
import java.util.UUID;

@Data
@Builder
public class LoginResponse {
    private String accessToken;
    private String tokenType;
    private long expiresIn;
    private UUID userId;
    private String username;
    private List<String> roles;
}
