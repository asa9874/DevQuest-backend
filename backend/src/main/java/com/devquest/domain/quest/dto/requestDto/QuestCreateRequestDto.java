package com.devquest.domain.quest.dto.requestDto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record QuestCreateRequestDto(
    @NotBlank(message = "제목은 필수 입력값입니다.")
    @Size(min = 2, max = 100, message = "제목은 2자 이상 100자 이하이어야 합니다.")
    String title,
    @NotBlank(message = "설명은 필수 입력값입니다.")
    @Size(min = 5, max = 500, message = "설명은 5자 이상 500자 이하이어야 합니다.")
    String description
) {}
