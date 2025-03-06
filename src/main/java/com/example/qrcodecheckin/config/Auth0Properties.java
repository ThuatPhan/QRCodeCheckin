package com.example.qrcodecheckin.config;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "auth0")
@FieldDefaults(level = AccessLevel.PRIVATE)
@Data
public class Auth0Properties {
    String clientId;
    String clientSecret;
    String audience;
    String grantType;
    String managementApi;
    String usersApi;
    String connection;
}
