package com.example.auth_service.dto;

import lombok.Data;

import java.util.List;
import java.util.UUID;

@Data
public class UserAuthResponse {
    private UUID userId;
    private String username;
    private boolean active;
    private List<String> roles;
}
