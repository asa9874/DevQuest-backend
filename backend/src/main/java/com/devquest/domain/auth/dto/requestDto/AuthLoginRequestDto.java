package com.devquest.domain.auth.dto.requestDto;

public record AuthLoginRequestDto(
        String email,
        String password
) {

}
