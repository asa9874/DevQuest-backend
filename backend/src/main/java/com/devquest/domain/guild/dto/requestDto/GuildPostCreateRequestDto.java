package com.devquest.domain.guild.dto.requestDto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record GuildPostCreateRequestDto(
    @NotNull(message = "길드 ID는 필수입니다.")
    Long guildId,
    @NotBlank(message = "제목은 필수입니다.")
    @Size(min = 2, max = 50, message = "제목은 2자 이상 50자 이하이어야 합니다.")
    String title,
    @NotBlank(message = "내용은 필수입니다.")
    @Size(min = 2, max = 500, message = "내용은 2자 이상 500자 이하이어야 합니다.")
    String content
) {}