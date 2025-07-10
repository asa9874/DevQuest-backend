package com.devquest.domain.monster.model;

import com.devquest.domain.member.model.Member;

import jakarta.persistence.Column;
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
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "creater_id", nullable = false)
    private Member creater;

    @Builder public Monster(String name, String description, String difficulty, Integer required_correct_count, Member creater) {
        this.name = name;
        this.description = description;
        this.difficulty = difficulty;
        this.required_correct_count = required_correct_count;
        this.creater = creater;
    }
}
