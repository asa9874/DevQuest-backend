package com.devquest.domain.ai.dto.requestDto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AiQuizBatchRequestDto {

    @NotBlank(message = "이름은 필수 입력값입니다.")
    private String name;

    @NotBlank(message = "설명은 필수 입력값입니다.")
    private String description;

    @Min(value = 1, message = "퀴즈 개수는 최소 1개 이상이어야 합니다.")
    @Max(value = 10, message = "퀴즈 개수는 최대 10개까지 생성 가능합니다.")
    @Builder.Default
    private int number = 1;
}
