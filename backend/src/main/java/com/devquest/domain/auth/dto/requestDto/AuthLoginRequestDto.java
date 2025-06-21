package com.devquest.domain.auth.dto.requestDto;

public record AuthLoginRequestDto(
        String email,
        String password) {
    public AuthLoginRequestDto {
        if (email == null) {
            throw new IllegalArgumentException("이메일이 비어 있을 수 없습니다.");
        }
        if (password == null || password.isBlank()) {
            throw new IllegalArgumentException("비밀번호가 비어 있을 수 없습니다.");
        }
    }

}
