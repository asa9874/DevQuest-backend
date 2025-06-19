package com.devquest.global.components;

import java.io.IOException;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import com.devquest.domain.auth.model.CustomOAuth2User;
import com.devquest.global.jwt.JwtTokenProvider;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class OAuth2LoginSuccessHandler implements AuthenticationSuccessHandler {
    private final JwtTokenProvider jwtTokenProvider; 

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
            HttpServletResponse response,
            Authentication authentication)
            throws IOException, ServletException {
        CustomOAuth2User oauthUser = (CustomOAuth2User) authentication.getPrincipal();
        String email = oauthUser.getEmail();
        String role = oauthUser.getAuthorities().iterator().next().getAuthority(); // ex. ROLE_CUSTOMER
        Long id = oauthUser.getMember().getId();
        String token = jwtTokenProvider.createToken(email, role, id);
        response.setHeader("Authorization", "Bearer " + token);
        String redirectUrl = "http://localhost:5173/oauth2/redirect?token=" + token;
        response.sendRedirect(redirectUrl);
    }
}