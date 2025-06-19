package com.devquest.domain.auth.dto.requestDto;

public record AuthRegisterRequestDto(
        String name,
        String email,
        String password
) {
    public AuthRegisterRequestDto {
        if (name == null || name.isBlank()) {
                throw new IllegalArgumentException("이름은 비어 있을 수 없습니다.");
        }
        if (email == null ) {
                throw new IllegalArgumentException("이메일이 비어 있을 수 없습니다.");
        }
        if (password == null || password.length() < 6) {
                throw new IllegalArgumentException("비밀번호는 최소 6자 이상이어야 합니다.");
        }
    }
}