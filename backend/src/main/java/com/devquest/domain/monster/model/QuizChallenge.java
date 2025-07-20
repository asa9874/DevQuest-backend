package com.devquest.domain.monster.model;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class QuizChallenge {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "monster_challenge_id", nullable = false)
    private MonsterChallenge monsterChallenge;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "quiz_id", nullable = false)
    private Quiz quiz;

    private Integer selectedOption;

    private Boolean isCorrect;

    @Builder
    public QuizChallenge(MonsterChallenge monsterChallenge, Quiz quiz) {
        this.monsterChallenge = monsterChallenge;
        this.quiz = quiz;
        this.selectedOption = null;
        this.isCorrect = false;
    }

    public boolean isSolved() {
        return selectedOption != null;
    }

    public void setSelectedOption(Integer selectedOption) {
        this.selectedOption = selectedOption;
        this.isCorrect = quiz.getAnswer().equals(selectedOption);
    }
}
