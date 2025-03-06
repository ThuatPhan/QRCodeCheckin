package com.example.qrcodecheckin.config;

import org.springframework.core.convert.converter.Converter;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class CustomJwtAuthenticationConverter implements Converter<Jwt, AbstractAuthenticationToken> {

    @Override
    public AbstractAuthenticationToken convert(Jwt jwt) {
        JwtAuthenticationConverter jwtAuthenticationConverter = new JwtAuthenticationConverter();
        jwtAuthenticationConverter.setJwtGrantedAuthoritiesConverter(this::extractPermissions);
        return jwtAuthenticationConverter.convert(jwt);
    }

    private Collection<GrantedAuthority> extractPermissions(Jwt jwt) {
        List<String> permissions = jwt.getClaimAsStringList("permissions");
        if (permissions == null) {
            return List.of();
        }
        return permissions.stream().map(permission -> (GrantedAuthority) () -> permission).collect(Collectors.toList());
    }
}
