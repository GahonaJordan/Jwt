package com.example.auth_service.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class TokenValidationResponse {
    private boolean valid;
    private String username;
    private List<String> roles;
}
