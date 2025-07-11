package com.devquest.domain.skill.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.devquest.domain.auth.util.AuthUtil;
import com.devquest.domain.member.model.Member;
import com.devquest.domain.member.repository.MemberRepository;
import com.devquest.domain.skill.dto.responseDto.MemberSkillResponseDto;
import com.devquest.domain.skill.model.MemberSkill;
import com.devquest.domain.skill.model.Skill;
import com.devquest.domain.skill.repository.MemberSkillRepository;
import com.devquest.domain.skill.repository.SkillRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MemberSkillService {
    private final MemberSkillRepository memberSkillRepository;
    private final SkillRepository skillRepository;
    private final MemberRepository memberRepository;

    public void createMemberSkill(Long memberId, Long skillId) {
        if (!AuthUtil.isAdminOrEqualMember(memberId)) {
            throw new IllegalArgumentException("권한이 없습니다.");
        }

        if (memberSkillRepository.existsByMemberIdAndSkillId(memberId, skillId)) {
            throw new IllegalArgumentException("이미 습득한 스킬입니다.");
        }

        // TODO: 여기부분에 이제 사전 조건 검사 로직 들어감 ㅎ

        Skill skill = skillRepository.findById(skillId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 스킬입니다."));
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 회원입니다."));
        MemberSkill memberSkill = MemberSkill.builder()
                .member(member)
                .skill(skill)
                .build();
        memberSkillRepository.save(memberSkill);
    }

    public void deleteMemberSkill(Long memberId, Long skillId) {
        if (!AuthUtil.isAdminOrEqualMember(memberId)) {
            throw new IllegalArgumentException("권한이 없습니다.");
        }

        MemberSkill memberSkill = memberSkillRepository.findByMemberIdAndSkillId(memberId, skillId)
                .orElseThrow(() -> new IllegalArgumentException("습득하지 않는 스킬입니다."));

        memberSkillRepository.delete(memberSkill);
    }

    public List<MemberSkillResponseDto> getMemberSkills(Long memberId) {
        if (!AuthUtil.isAdminOrEqualMember(memberId)) {
            throw new IllegalArgumentException("권한이 없습니다.");
        }
        return memberSkillRepository.findAllDtoByMemberId(memberId);
    }

    public MemberSkillResponseDto getMemberSkill(Long memberId, Long skillId) {
        if (!AuthUtil.isAdminOrEqualMember(memberId)) {
            throw new IllegalArgumentException("권한이 없습니다.");
        }

        MemberSkillResponseDto responseDto = memberSkillRepository.findDtoByMemberIdAndSkillId(memberId, skillId)
                .orElseThrow(() -> new IllegalArgumentException("습득하지 않은 스킬입니다."));

        return responseDto;
    }
}
