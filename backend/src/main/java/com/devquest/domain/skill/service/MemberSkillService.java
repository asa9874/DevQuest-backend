package com.devquest.domain.skill.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.devquest.domain.auth.util.AuthUtil;
import com.devquest.domain.member.model.Member;
import com.devquest.domain.member.repository.MemberRepository;
import com.devquest.domain.monster.repository.MonsterRepository;
import com.devquest.domain.quest.repository.QuestRepository;
import com.devquest.domain.skill.dto.responseDto.MemberSkillResponseDto;
import com.devquest.domain.skill.model.MemberSkill;
import com.devquest.domain.skill.model.Skill;
import com.devquest.domain.skill.repository.MemberSkillRepository;
import com.devquest.domain.skill.repository.SkillRepository;
import com.devquest.domain.skill.repository.SkillRequiredMonsterRepository;
import com.devquest.domain.skill.repository.SkillRequiredQuestRepository;
import com.devquest.domain.skill.repository.SkillRequiredSkillRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MemberSkillService {
    private final MemberSkillRepository memberSkillRepository;
    private final SkillRepository skillRepository;
    private final SkillRequiredSkillRepository skillRequiredSkillRepository;
    private final SkillRequiredQuestRepository skillRequiredQuestRepository;
    private final SkillRequiredMonsterRepository skillRequiredMonsterRepository;
    private final MemberRepository memberRepository;

    public void createMemberSkill(Long memberId, Long skillId) {
        if (!AuthUtil.isAdminOrEqualMember(memberId)) {
            throw new IllegalArgumentException("권한이 없습니다.");
        }

        if (memberSkillRepository.existsByMemberIdAndSkillId(memberId, skillId)) {
            throw new IllegalArgumentException("이미 습득한 스킬입니다.");
        }

        // 사전 스킬
        List<Long> missingSkillIds = skillRequiredSkillRepository.findMissingRequiredSkillIds(memberId, skillId);
        if (!missingSkillIds.isEmpty()) {
            throw new IllegalArgumentException(
                    "해당 스킬을 습득하기 위해 다음 사전 스킬을 먼저 습득해야 합니다: " +
                            missingSkillIds.stream().map(id -> "ID: " + id).collect(Collectors.joining(", ")));
        }

        // 사전 몬스터
        List<Long> missingMonsterIds = skillRequiredMonsterRepository.findMissingRequiredMonsterIds(memberId, skillId);
        if (!missingMonsterIds.isEmpty()) {
            throw new IllegalArgumentException(
                    "해당 스킬을 습득하기 위해 다음 몬스터를 먼저 처치해야 합니다: " +
                            missingMonsterIds.stream().map(id -> "ID: " + id).collect(Collectors.joining(", ")));
        }

        // 사전 퀘스트
        List<Long> missingQuestIds = skillRequiredQuestRepository.findMissingRequiredQuestIds(memberId, skillId);
        if (!missingQuestIds.isEmpty()) {
            throw new IllegalArgumentException(
                    "해당 스킬을 습득하기 위해 다음 퀘스트를 먼저 완료해야 합니다: " +
                            missingQuestIds.stream().map(id -> "ID: " + id).collect(Collectors.joining(", ")));
        }

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
