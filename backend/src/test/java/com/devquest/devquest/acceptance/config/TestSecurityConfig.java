package com.devquest.devquest.acceptance.config;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.TestingAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;

import com.devquest.global.jwt.CustomUserDetails;

@Configuration
public class TestSecurityConfig {

    @Bean
    public Authentication getAuthentication() {
        List<SimpleGrantedAuthority> authorities = Arrays.asList("ROLE_USER", "ROLE_ADMIN")
                .stream()
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());

        CustomUserDetails userDetails = new CustomUserDetails(1L, "test@example.com", "password", authorities);
        return new PreAuthenticatedAuthenticationToken(userDetails, null, authorities);
    }

    public static void setSecurityContext(Long memberId, String email, String... roles) {
        List<SimpleGrantedAuthority> authorities = Arrays.stream(roles)
                .map(role -> new SimpleGrantedAuthority("ROLE_" + role))
                .collect(Collectors.toList());

        CustomUserDetails userDetails = new CustomUserDetails(memberId, email, "password", authorities);
        SecurityContext context = SecurityContextHolder.createEmptyContext();
        context.setAuthentication(new TestingAuthenticationToken(userDetails, null, authorities));
        SecurityContextHolder.setContext(context);
    }
}
