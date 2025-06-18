package com.devquest.domain.auth.dto.requestDto;

public record AuthRegisterRequestDto(
        String name,
        String email,
        String password
) {
}