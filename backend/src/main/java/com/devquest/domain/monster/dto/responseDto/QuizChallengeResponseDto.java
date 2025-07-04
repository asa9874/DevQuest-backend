package com.devquest.domain.monster.dto.responseDto;

public record QuizChallengeResponseDto(
    Long id,
    Long monsterChallengeId,
    Long quizId,
    String quizTitle,
    String quizQuestion,
    String option1,
    String option2,
    String option3,
    String option4,
    Integer correctAnswer,
    Integer selectedOption,
    Boolean isCorrect
) {
}
