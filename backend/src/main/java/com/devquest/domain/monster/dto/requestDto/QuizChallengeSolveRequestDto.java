package com.devquest.domain.monster.dto.requestDto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record QuizChallengeSolveRequestDto(
    @NotNull(message = "선택한 옵션은 필수입니다.")
    @Min(value = 1, message = "선택한 옵션은 1 이상이어야 합니다.")
    @Max(value = 4, message = "선택한 옵션은 4 이하여야 합니다.")
    Integer selectedOption
) {
}
