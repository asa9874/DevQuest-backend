package com.devquest.domain.member.dto.requestDto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record MemberUpdatePassswordRequetsDto(
    @NotBlank(message = "현재 비밀번호가 유효하지 않습니다.")
    @Size(min = 6, max = 100, message = "현재 비밀번호는 6자 이상 100자 이하이어야 합니다.")
    String currentPassword,
    @NotBlank(message = "새 비밀번호가 유효하지 않습니다.")
    @Size(min = 6, max = 100, message = "새 비밀번호는 6자 이상 100자 이하이어야 합니다.")
    String newPassword,
    @NotBlank(message = "확인 비밀번호가 유효하지 않습니다.")
    @Size(min = 6, max = 100, message = "확인 비밀번호는 6자 이상 100자 이하이어야 합니다.")
    String confirmNewPassword
) {}
