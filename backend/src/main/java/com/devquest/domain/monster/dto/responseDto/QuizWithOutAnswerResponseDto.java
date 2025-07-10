package com.devquest.domain.monster.dto.responseDto;

import com.devquest.domain.monster.model.Quiz;

public record QuizWithOutAnswerResponseDto(
    Long id,
    String title,
    String question,
    String option1,
    String option2,
    String option3,
    String option4
) {
    public static QuizWithOutAnswerResponseDto from (Quiz quiz) {
        return new QuizWithOutAnswerResponseDto(
            quiz.getId(),
            quiz.getTitle(),
            quiz.getQuestion(),
            quiz.getOption1(),
            quiz.getOption2(),
            quiz.getOption3(),
            quiz.getOption4()
        );
    }
}
