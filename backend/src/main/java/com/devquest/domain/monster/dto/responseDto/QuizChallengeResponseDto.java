package com.devquest.domain.monster.dto.responseDto;

import com.devquest.domain.monster.model.QuizChallenge;

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
    public static QuizChallengeResponseDto from(QuizChallenge quizChallenge) {
        return new QuizChallengeResponseDto(
            quizChallenge.getId(),
            quizChallenge.getMonsterChallenge().getId(),
            quizChallenge.getQuiz().getId(),
            quizChallenge.getQuiz().getTitle(),
            quizChallenge.getQuiz().getQuestion(),
            quizChallenge.getQuiz().getOption1(),
            quizChallenge.getQuiz().getOption2(),
            quizChallenge.getQuiz().getOption3(),
            quizChallenge.getQuiz().getOption4(),
            quizChallenge.getQuiz().getAnswer(),
            quizChallenge.getSelectedOption(),
            quizChallenge.getIsCorrect()
        );
    }
}
