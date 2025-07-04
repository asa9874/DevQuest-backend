package com.devquest.domain.monster.dto.responseDto;

public record QuizResponseDto(
    Long id,
    Long monsterId,
    String monsterName,
    String title,
    String question,
    String option1,
    String option2,
    String option3,
    String option4,
    Integer answer,
    Integer challengeCount,
    Integer correctCount
) {
}
