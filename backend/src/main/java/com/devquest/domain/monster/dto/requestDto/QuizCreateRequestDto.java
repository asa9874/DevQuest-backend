package com.devquest.domain.monster.dto.requestDto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record QuizCreateRequestDto(
    @NotNull(message = "몬스터 ID는 필수입니다.")
    Long monsterId,
    
    @NotBlank(message = "퀴즈 제목은 필수입니다.")
    @Size(max = 20, message = "퀴즈 제목은 20자 이하여야 합니다.")
    String title,
    
    @NotBlank(message = "퀴즈 질문은 필수입니다.")
    @Size(max = 500, message = "퀴즈 질문은 500자 이하여야 합니다.")
    String question,
    
    @NotBlank(message = "선택지 1은 필수입니다.")
    @Size(max = 100, message = "선택지 1은 100자 이하여야 합니다.")
    String option1,
    
    @NotBlank(message = "선택지 2는 필수입니다.")
    @Size(max = 100, message = "선택지 2는 100자 이하여야 합니다.")
    String option2,
    
    @NotBlank(message = "선택지 3은 필수입니다.")
    @Size(max = 100, message = "선택지 3은 100자 이하여야 합니다.")
    String option3,
    
    @NotBlank(message = "선택지 4는 필수입니다.")
    @Size(max = 100, message = "선택지 4는 100자 이하여야 합니다.")
    String option4,
    
    @NotNull(message = "정답은 필수입니다.")
    @Min(value = 1, message = "정답은 1 이상이어야 합니다.")
    @Max(value = 4, message = "정답은 4 이하여야 합니다.")
    Integer answer
) {
}
