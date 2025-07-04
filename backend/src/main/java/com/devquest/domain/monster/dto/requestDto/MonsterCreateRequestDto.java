package com.devquest.domain.monster.dto.requestDto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record MonsterCreateRequestDto(
    @NotBlank(message = "몬스터 이름은 필수입니다.")
    @Size(max = 20, message = "몬스터 이름은 20자 이하여야 합니다.")
    String name,
    
    @NotBlank(message = "몬스터 설명은 필수입니다.")
    @Size(max = 500, message = "몬스터 설명은 500자 이하여야 합니다.")
    String description,
    
    @NotBlank(message = "난이도는 필수입니다.")
    String difficulty,
    
    @NotNull(message = "필요한 정답 개수는 필수입니다.")
    @Min(value = 1, message = "필요한 정답 개수는 1 이상이어야 합니다.")
    Integer requiredCorrectCount
) {
}
