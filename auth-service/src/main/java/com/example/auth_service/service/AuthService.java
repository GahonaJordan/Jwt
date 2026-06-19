package com.example.auth_service.service;

import com.example.auth_service.client.UsuariosClient;
import com.example.auth_service.dto.LoginRequest;
import com.example.auth_service.dto.LoginResponse;
import com.example.auth_service.dto.TokenValidationResponse;
import com.example.auth_service.dto.UserAuthResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientResponseException;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UsuariosClient usuariosClient;
    private final JwtService jwtService;

    public LoginResponse login(LoginRequest request) {
        UserAuthResponse user = validateWithUsuarios(request);

        String token = jwtService.generateToken(user.getUserId(), user.getUsername(), user.getRoles());

        return LoginResponse.builder()
                .accessToken(token)
                .tokenType("Bearer")
                .expiresIn(jwtService.getExpirationMs() / 1000)
                .userId(user.getUserId())
                .username(user.getUsername())
                .roles(user.getRoles())
                .build();
    }

    public TokenValidationResponse validateToken(String token) {
        if (token == null || !jwtService.isTokenValid(token)) {
            return TokenValidationResponse.builder().valid(false).build();
        }

        return TokenValidationResponse.builder()
                .valid(true)
                .username(jwtService.extractUsername(token))
                .roles(jwtService.extractRoles(token))
                .build();
    }

    private UserAuthResponse validateWithUsuarios(LoginRequest request) {
        try {
            return usuariosClient.validateCredentials(request);
        } catch (RestClientResponseException ex) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Credenciales inválidas");
        }
    }
}
