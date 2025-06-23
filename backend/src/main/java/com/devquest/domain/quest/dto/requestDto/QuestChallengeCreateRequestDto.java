package com.devquest.domain.quest.dto.requestDto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record QuestChallengeCreateRequestDto(
    @NotNull(message = "멤버 ID는 필수 입력값입니다.")
    @Min(value = 1, message = "멤버 ID는 1 이상이어야 합니다.")
    Long memberId,
    @NotNull(message = "퀘스트 ID는 필수 입력값입니다.")
    @Min(value = 1, message = "퀘스트 ID는 1 이상이어야 합니다.")
    Long questId
) {}
