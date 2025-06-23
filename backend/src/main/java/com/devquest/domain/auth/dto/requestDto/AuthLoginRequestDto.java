package com.devquest.domain.auth.dto.requestDto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record AuthLoginRequestDto(
    @NotBlank(message = "이메일은 비어 있을 수 없습니다.")
    @Email(message = "이메일 형식이 올바르지 않습니다.")
    @Size(min = 5, max = 100, message = "이메일은 5자 이상 100자 이하이어야 합니다.")
    String email,
    @NotBlank(message = "비밀번호는 비어 있을 수 없습니다.")
    @Size(min = 6, max = 100, message = "비밀번호는 6자 이상 100자 이하이어야 합니다.")
    String password
) {}
