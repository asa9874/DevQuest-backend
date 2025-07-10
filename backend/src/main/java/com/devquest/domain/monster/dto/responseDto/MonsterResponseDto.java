package com.devquest.domain.monster.dto.responseDto;

import com.devquest.domain.monster.model.Monster;

public record MonsterResponseDto(
    Long id,
    String name,
    String description,
    String difficulty,
    Integer requiredCorrectCount
) {
    public static MonsterResponseDto from(Monster monster) {
        return new MonsterResponseDto(
            monster.getId(),
            monster.getName(),
            monster.getDescription(),
            monster.getDifficulty(),
            monster.getRequired_correct_count()
        );
    }
}
