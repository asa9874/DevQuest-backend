package com.devquest.domain.monster.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Quiz {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "monster_id", nullable = false)
    private Monster monster;

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

    @Builder
    public Quiz(Monster monster, String title, String question, String option1, String option2, String option3,
            String option4, Integer answer) {
        this.monster = monster;
        this.title = title;
        this.question = question;
        this.option1 = option1;
        this.option2 = option2;
        this.option3 = option3;
        this.option4 = option4;
        this.answer = answer;
    }
}
