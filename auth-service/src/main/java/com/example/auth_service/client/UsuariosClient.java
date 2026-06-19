package com.example.auth_service.client;

import com.example.auth_service.dto.LoginRequest;
import com.example.auth_service.dto.UserAuthResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestClientResponseException;

@Component
public class UsuariosClient {

    private final RestClient restClient;

    public UsuariosClient(@Value("${usuarios.service.url}") String usuariosServiceUrl) {
        this.restClient = RestClient.builder()
                .baseUrl(usuariosServiceUrl)
                .build();
    }

    public UserAuthResponse validateCredentials(LoginRequest request) {
        try {
            return restClient.post()
                    .uri("/api/auth/validate")
                    .body(request)
                    .retrieve()
                    .onStatus(HttpStatusCode::isError, (req, res) -> {
                        throw new RestClientResponseException(
                                "Credenciales inválidas",
                                res.getStatusCode().value(),
                                res.getStatusText(),
                                res.getHeaders(),
                                null,
                                null);
                    })
                    .body(UserAuthResponse.class);
        } catch (RestClientResponseException ex) {
            throw ex;
        }
    }
}
