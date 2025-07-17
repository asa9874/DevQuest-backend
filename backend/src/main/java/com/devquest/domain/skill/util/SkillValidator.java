package com.devquest.domain.skill.util;

import org.springframework.stereotype.Component;

import com.devquest.domain.auth.util.AuthUtil;
import com.devquest.domain.skill.repository.MemberSkillRepository;
import com.devquest.domain.skill.repository.SkillRepository;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class SkillValidator {
    private final SkillRepository skillRepository;
    private final MemberSkillRepository memberSkillRepository;
    
    public boolean isSkillOwner(Long skillId, Long memberId) {
        return AuthUtil.isAdmin() || 
                skillRepository.findById(skillId)
                    .map(skill -> skill.getCreater().getId().equals(memberId))
                    .orElse(false);
    }
    
    public boolean hasMemberSkill(Long memberId, Long skillId) {
        return AuthUtil.isAdmin() ||
                memberSkillRepository.existsByMemberIdAndSkillId(memberId, skillId);
    }
}
