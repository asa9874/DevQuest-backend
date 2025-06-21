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
        if (name.length() < 2 || name.length() > 50) {
                throw new IllegalArgumentException("이름은 2자 이상 50자 이하이어야 합니다.");
        }
        if (email == null ) {
                throw new IllegalArgumentException("이메일이 비어 있을 수 없습니다.");
        }
        if (email.length() > 100 || email.length() < 5) {
                throw new IllegalArgumentException("이메일은 최소 5자 이상, 최대 100자 이하여야 합니다.");
        }
        if (password == null || password.length() < 6 || password.length() > 100) {
                throw new IllegalArgumentException("비밀번호는 최소 6자 이상, 최대 100자 이하여야 합니다.");
        }
    }
}