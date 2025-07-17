package com.devquest.domain.skill.service;

import java.util.List;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

import com.devquest.domain.auth.util.AuthUtil;
import com.devquest.domain.member.model.Member;
import com.devquest.domain.member.repository.MemberRepository;
import com.devquest.domain.skill.dto.requestDto.SkillCreateRequestDto;
import com.devquest.domain.skill.dto.requestDto.SkillUpdateRequestDto;
import com.devquest.domain.skill.dto.responseDto.SkillResponseDto;
import com.devquest.domain.skill.model.Skill;
import com.devquest.domain.skill.repository.SkillRepository;
import com.devquest.global.exception.customException.DuplicateDataException;
import jakarta.persistence.EntityNotFoundException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SkillService {
    private final SkillRepository skillRepository;
    private final MemberRepository memberRepository;

    public List<SkillResponseDto> getSkills() {
        return skillRepository.findAll().stream()
                .map(SkillResponseDto::from)
                .toList();
    }

    public SkillResponseDto getSkill(Long skillId) {
        Skill skill = skillRepository.findById(skillId)
                .orElseThrow(() -> new EntityNotFoundException("스킬을 찾을 수 없습니다."));
        return SkillResponseDto.from(skill);
    }

    public void createSkill(
            SkillCreateRequestDto requestDto,
            Long memberId) {
        Member creater = memberRepository.findById(memberId)
                .orElseThrow(() -> new EntityNotFoundException("회원이 존재하지 않습니다. "));
        if (skillRepository.existsByName(requestDto.name())) {
            throw new DuplicateDataException("이미 존재하는 스킬입니다.");
        }
        Skill skill = Skill.builder()
                .name(requestDto.name())
                .description(requestDto.description())
                .creater(creater)
                .build();
        skillRepository.save(skill);
    }

    public void updateSkill(
            Long skillId,
            SkillUpdateRequestDto requestDto) {
        Skill skill = skillRepository.findById(skillId)
                .orElseThrow(() -> new EntityNotFoundException("스킬을 찾을 수 없습니다. "));

        if (skillRepository.existsByName(requestDto.name())) {
            throw new DuplicateDataException("이미 존재하는 스킬입니다.");
        }

        if (!AuthUtil.isAdminOrEqualMember(skill.getCreater().getId())) {
            throw new AccessDeniedException("권한이 없습니다.");
        }

        skill.update(requestDto.name(), requestDto.description());
        skillRepository.save(skill);
    }

    public void deleteSkill(Long skillId) {
        Skill skill = skillRepository.findById(skillId)
                .orElseThrow(() -> new EntityNotFoundException("스킬을 찾을 수 없습니다. "));

        if (!AuthUtil.isAdminOrEqualMember(skill.getCreater().getId())) {
            throw new AccessDeniedException("권한이 없습니다.");
        }

        skillRepository.delete(skill);
    }
}
