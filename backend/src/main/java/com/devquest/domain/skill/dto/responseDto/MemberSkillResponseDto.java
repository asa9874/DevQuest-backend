package com.devquest.domain.skill.dto.responseDto;

import java.time.LocalDateTime;

import com.devquest.domain.skill.model.MemberSkill;

public record MemberSkillResponseDto(
    Long id,
    Long memberId,
    Long skillId,
    String skillName,
    String skillDescription,
    LocalDateTime acquiredAt
) {
    public static MemberSkillResponseDto from(MemberSkill memberSkill) {
        return new MemberSkillResponseDto(
            memberSkill.getId(),
            memberSkill.getMember().getId(),
            memberSkill.getSkill().getId(),
            memberSkill.getSkill().getName(),
            memberSkill.getSkill().getDescription(),
            memberSkill.getAcquiredAt()
        );
    }
}
