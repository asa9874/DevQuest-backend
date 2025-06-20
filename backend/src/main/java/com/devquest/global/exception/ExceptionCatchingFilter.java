package com.devquest.global.exception;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.data.redis.RedisConnectionFailureException;
import org.springframework.http.HttpStatus;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
public class ExceptionCatchingFilter extends OncePerRequestFilter {

    private static final Logger log = LoggerFactory.getLogger(ExceptionCatchingFilter.class);

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain)
            throws ServletException, IOException {
        try {
            filterChain.doFilter(request, response);
        } catch (RedisConnectionFailureException ex) {
            log.error("Redis connection error: {}", ex.getMessage());
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            response.setContentType("text/plain;charset=UTF-8");
            response.getWriter().write("Redis connection error: " + ex.getMessage());
        } catch (JwtException ex) {
            log.error("JWT error: {}", ex.getMessage());
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            response.setContentType("text/plain;charset=UTF-8");
            response.getWriter().write("Invalid JWT token: " + ex.getMessage());
        } catch (Exception ex) {
            log.error("Unexpected error: {}", ex.getMessage());
            response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.setContentType("text/plain;charset=UTF-8");
            response.getWriter().write("Unexpected error: " + ex.getMessage());
            throw ex;
        }
    }
}
