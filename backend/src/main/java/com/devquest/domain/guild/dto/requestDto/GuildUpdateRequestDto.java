package com.devquest.domain.guild.dto.requestDto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record GuildUpdateRequestDto(
    @NotBlank(message = "길드 이름은 비어있을 수 없습니다.")
    @Size(min = 2, max = 20, message = "길드 이름은 2자 이상 20자 이하이어야 합니다.")
    String name,
    @NotBlank(message = "길드 설명은 비어있을 수 없습니다.")
    @Size(min = 5, max = 100, message = "길드 설명은 5자 이상 100자 이하이어야 합니다.")
    String description
) {}
