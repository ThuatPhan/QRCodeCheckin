package com.example.qrcodecheckin.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.web.SecurityFilterChain;

import javax.crypto.spec.SecretKeySpec;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    @Value("${jwt.signerKey}")
    String SIGNER_KEY;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.authorizeHttpRequests(request ->
                request.requestMatchers(HttpMethod.POST, "/api/auth").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/users/**").hasAuthority("SCOPE_ADMIN")
                        .requestMatchers(HttpMethod.POST, "/api/users/**").hasAuthority("SCOPE_ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/api/users/**").hasAuthority("SCOPE_ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/api/users/**").hasAuthority("SCOPE_ADMIN")
                        .requestMatchers(HttpMethod.GET, "/api/departments/**").hasAuthority("SCOPE_ADMIN")
                        .requestMatchers(HttpMethod.POST, "/api/departments/**").hasAuthority("SCOPE_ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/api/departments/**").hasAuthority("SCOPE_ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/api/departments/**").hasAuthority("SCOPE_ADMIN")
                        .requestMatchers(HttpMethod.GET, "/api/employees/**").hasAuthority("SCOPE_ADMIN")
                        .requestMatchers(HttpMethod.POST, "/api/employees/**").hasAuthority("SCOPE_ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/api/employees/**").hasAuthority("SCOPE_ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/api/employees/**").hasAuthority("SCOPE_ADMIN")
                        .requestMatchers(HttpMethod.GET, "/api/locations/**").hasAuthority("SCOPE_ADMIN")
                        .requestMatchers(HttpMethod.POST, "/api/locations/**").hasAuthority("SCOPE_ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/api/locations/**").hasAuthority("SCOPE_ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/api/locations/**").hasAuthority("SCOPE_ADMIN")
                        .requestMatchers(HttpMethod.POST, "/api/qrcode/**").hasAuthority("SCOPE_ADMIN")
                        .anyRequest().authenticated()
        );
        httpSecurity.oauth2ResourceServer(oauth2 ->
                oauth2.jwt(jwtConfigurer -> jwtConfigurer.decoder(jwtDecoder()))
        );
        httpSecurity.csrf(AbstractHttpConfigurer::disable);
        return httpSecurity.build();
    }

    @Bean
    public JwtDecoder jwtDecoder() {
        SecretKeySpec secretKeySpec = new SecretKeySpec(SIGNER_KEY.getBytes(), "HS512");
        return NimbusJwtDecoder
                .withSecretKey(secretKeySpec)
                .macAlgorithm(MacAlgorithm.HS512)
                .build();

    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}