package com.devquest.domain.monster.dto.responseDto;

public record MonsterResponseDto(
    Long id,
    String name,
    String description,
    String difficulty,
    Integer requiredCorrectCount,
    Integer quizCount
) {
}
