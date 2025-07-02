package com.devquest.domain.monster.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Monster {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true , length = 20)
    private String name;

    @Column(nullable = false, length = 500)
    private String description;

    @Column(nullable = false)
    private String difficulty;

    @Builder
    public Monster(String name, String description, String difficulty) {
        this.name = name;
        this.description = description;
        this.difficulty = difficulty;
    }
}
