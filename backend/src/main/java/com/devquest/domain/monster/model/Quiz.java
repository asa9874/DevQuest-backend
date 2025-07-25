package com.devquest.domain.monster.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

import com.devquest.domain.member.model.Member;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Quiz {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 20)
    private String title;

    @Column(nullable = false, length = 500)
    private String question;

    @Column(nullable = false, length = 100)
    private String option1;

    @Column(nullable = false, length = 100)
    private String option2;

    @Column(nullable = false, length = 100)
    private String option3;

    @Column(nullable = false, length = 100)
    private String option4;

    @Column(nullable = false)
    private Integer answer;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "creater_id", nullable = false)
    private Member creater;

    @Builder
    public Quiz(String title, String question, String option1, String option2, String option3,
            String option4, Integer answer, Member creater) {
        this.title = title;
        this.question = question;
        this.option1 = option1;
        this.option2 = option2;
        this.option3 = option3;
        this.option4 = option4;
        this.answer = answer;
        this.creater = creater;
    }

    public void update(String title, String question, String option1, String option2, String option3, String option4,
            Integer answer) {
        this.title = title;
        this.question = question;
        this.option1 = option1;
        this.option2 = option2;
        this.option3 = option3;
        this.option4 = option4;
        this.answer = answer;
    }
}
