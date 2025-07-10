package com.devquest.domain.skill.dto.responseDto;

import com.devquest.domain.skill.model.Skill;

public record SkillResponseDto(
    Long id,
    String name,
    String description
) {
    public static SkillResponseDto from(Skill skill) {
        return new SkillResponseDto(
            skill.getId(),
            skill.getName(),
            skill.getDescription()
        );
    }
}
