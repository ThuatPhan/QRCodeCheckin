package com.example.qrcodecheckin.service;

import com.example.qrcodecheckin.dto.request.AuthenticationRequest;
import com.example.qrcodecheckin.dto.response.AuthenticationResponse;
import com.example.qrcodecheckin.entity.Role;
import com.example.qrcodecheckin.entity.User;
import com.example.qrcodecheckin.exception.AppException;
import com.example.qrcodecheckin.exception.ErrorCode;
import com.example.qrcodecheckin.repository.UserRepository;
import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jwt.JWTClaimsSet;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

import lombok.experimental.NonFinal;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.StringJoiner;


@Slf4j
@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class AuthenticationService {
    UserRepository userRepository;
    PasswordEncoder passwordEncoder;

    @NonFinal
    @Value("${jwt.signerKey}")
    String SIGNER_KEY;

    public AuthenticationResponse authenticate(AuthenticationRequest authenticationRequest) {
        User user = userRepository
                .findByUsername(authenticationRequest.getUsername())
                .orElseThrow(() -> new AppException(ErrorCode.USERNAME_NOT_EXIST));

        if (!passwordEncoder.matches(authenticationRequest.getPassword(), user.getPassword())) {
            throw new AppException(ErrorCode.UNAUTHENTICATED);
        }

        return AuthenticationResponse
                .builder()
                .accessToken(generateToken(user))
                .build();
    }

    private String generateToken(User user) {
        JWSHeader header = new JWSHeader(JWSAlgorithm.HS512);
        JWTClaimsSet claims = new JWTClaimsSet.Builder()
                .subject(user.getUsername())
                .issuer("thuatphan.com")
                .issueTime(new Date())
                .expirationTime(new Date(Instant.now().plus(1, ChronoUnit.HOURS).toEpochMilli()))
                .claim("scope", buildScope(user))
                .build();
        Payload payload = new Payload(claims.toJSONObject());

        JWSObject jwsObject = new JWSObject(header, payload);

        try {
            jwsObject.sign(new MACSigner(SIGNER_KEY.getBytes()));
            return jwsObject.serialize();
        } catch (JOSEException e) {
            log.error("Failed to generate token");
            throw new AppException(ErrorCode.GENERATE_TOKEN_FAILED);
        }
    }

    private String buildScope(User user) {
        StringJoiner scopeJoiner = new StringJoiner(" ");
        if (user.getRoles() != null) {
            // Create a new collection to avoid concurrent modification
            List<Role> rolesCopy = new ArrayList<>(user.getRoles());
            for (Role role : rolesCopy) {
                if (role.getName() != null) {
                    scopeJoiner.add(role.getName().name());
                }
            }
        }
        return scopeJoiner.toString();
    }
}
