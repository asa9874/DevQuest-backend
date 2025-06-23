package com.devquest.domain.member.dto.requestDto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record MemberUpdateRequestDto(
    @NotBlank(message = "이름은 비어 있을 수 없습니다.")
    @Size(min = 2, max = 50, message = "이름은 2자 이상 50자 이하이어야 합니다.")
    String name
) {}
