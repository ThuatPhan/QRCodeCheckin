package com.example.qrcodecheckin.service;


import com.example.qrcodecheckin.config.Auth0Properties;
import com.example.qrcodecheckin.dto.response.Auth0TokenResponse;
import com.example.qrcodecheckin.exception.AppException;
import com.example.qrcodecheckin.exception.ErrorCode;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.util.Map;
import java.util.Objects;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class Auth0Service {
    Auth0Properties auth0Properties;
    RestClient restClient;

    @Autowired
    public Auth0Service(Auth0Properties auth0Properties) {
        this.auth0Properties = auth0Properties;
        this.restClient = RestClient.create();
    }

    private String getAccessToken() {
        Map<String, String> requestBody = Map.of(
                "client_id", auth0Properties.getClientId(),
                "client_secret", auth0Properties.getClientSecret(),
                "audience", auth0Properties.getAudience(),
                "grant_type", auth0Properties.getGrantType()
        );
        return Objects.requireNonNull(restClient.post()
                        .uri(auth0Properties.getManagementApi())
                        .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                        .body(requestBody)
                        .retrieve()
                        .onStatus(HttpStatusCode::isError, (request, response) -> {
                            throw new AppException(ErrorCode.FAILED_TO_GET_TOKEN);
                        })
                        .body(Auth0TokenResponse.class))
                .access_token();

    }

    public void createUserAccount(String email, String password) {
        Map<String, String> requestBody = Map.of(
                "email", email,
                "password", password,
                "connection", auth0Properties.getConnection()
        );
        restClient.post()
                .uri(auth0Properties.getUsersApi())
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + getAccessToken())
                .body(requestBody)
                .retrieve()
                .onStatus(HttpStatusCode::isError, (request, response) -> {
                    throw new AppException(ErrorCode.FAILED_TO_CREATE_ACCOUNT);
                })
                .toEntity(String.class);
    }
}
