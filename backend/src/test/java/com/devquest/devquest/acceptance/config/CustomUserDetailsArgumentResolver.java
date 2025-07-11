package com.devquest.devquest.acceptance.config;

import org.springframework.core.MethodParameter;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import com.devquest.global.jwt.CustomUserDetails;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class CustomUserDetailsArgumentResolver implements HandlerMethodArgumentResolver {

    @Override
    public boolean supportsParameter(@NonNull MethodParameter parameter) {
        return parameter.getParameterType().equals(CustomUserDetails.class);
    }

    @Override
    public Object resolveArgument(@NonNull MethodParameter parameter, @Nullable ModelAndViewContainer mavContainer,
                                 @NonNull NativeWebRequest webRequest, @Nullable WebDataBinderFactory binderFactory) {
        List<SimpleGrantedAuthority> authorities = Arrays.asList("ROLE_USER", "ROLE_ADMIN")
                .stream()
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
        
        return new CustomUserDetails(1L, "test@example.com", "password123", authorities);
    }
}
