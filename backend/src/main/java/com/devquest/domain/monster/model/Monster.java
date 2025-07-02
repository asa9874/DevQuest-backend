package com.devquest.domain.monster.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Monster {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 20)
    private String name;

    @Column(nullable = false, length = 500)
    private String description;

    @Column(nullable = false)
    private String difficulty;

    @Column(nullable = false)
    private Integer required_correct_count;

    @Builder public Monster(String name, String description, String difficulty, Integer required_correct_count) {
        this.name = name;
        this.description = description;
        this.difficulty = difficulty;
        this.required_correct_count = required_correct_count;
    }
}
