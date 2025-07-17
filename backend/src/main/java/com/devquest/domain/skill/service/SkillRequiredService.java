package com.devquest.domain.skill.service;

import java.util.List;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.devquest.domain.auth.util.AuthUtil;
import com.devquest.domain.monster.dto.responseDto.MonsterResponseDto;
import com.devquest.domain.monster.model.Monster;
import com.devquest.domain.monster.repository.MonsterRepository;
import com.devquest.domain.quest.dto.responseDto.QuestResponseDto;
import com.devquest.domain.quest.model.Quest;
import com.devquest.domain.quest.repository.QuestRepository;
import com.devquest.domain.skill.dto.responseDto.SkillResponseDto;
import com.devquest.domain.skill.model.Skill;
import com.devquest.domain.skill.model.SkillRequiredMonster;
import com.devquest.domain.skill.model.SkillRequiredQuest;
import com.devquest.domain.skill.model.SkillRequiredSkill;
import com.devquest.domain.skill.repository.SkillRepository;
import com.devquest.domain.skill.repository.SkillRequiredMonsterRepository;
import com.devquest.domain.skill.repository.SkillRequiredQuestRepository;
import com.devquest.domain.skill.repository.SkillRequiredSkillRepository;
import com.devquest.domain.skill.util.SkillValidator;
import com.devquest.global.exception.customException.CyclicReferenceException;
import com.devquest.global.exception.customException.DuplicateDataException;
import com.devquest.global.exception.customException.SelfReferenceException;
import jakarta.persistence.EntityNotFoundException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SkillRequiredService {
    private final SkillRequiredMonsterRepository skillRequiredMonsterRepository;
    private final SkillRequiredQuestRepository skillRequiredQuestRepository;
    private final SkillRequiredSkillRepository skillRequiredSkillRepository;
    private final QuestRepository questRepository;
    private final MonsterRepository monsterRepository;
    private final SkillRepository skillRepository;
    private final SkillValidator skillValidator;

    public List<QuestResponseDto> getSkillRequiredQuests(Long skillId) {
        if (!skillRepository.existsById(skillId)) {
            throw new EntityNotFoundException("존재하지 않는 스킬입니다.");
        }

        if (!skillValidator.isSkillOwner(skillId, AuthUtil.getCurrentMemberId())) {
            throw new AccessDeniedException("권한이 없습니다.");
        }

        List<Quest> requiredQuests = skillRequiredQuestRepository.findAllBySkillId(skillId);

        return requiredQuests.stream()
                .map(QuestResponseDto::from)
                .toList();
    }

    public List<SkillResponseDto> getSkillRequiredSkills(Long skillId) {
        if (!skillRepository.existsById(skillId)) {
            throw new EntityNotFoundException("존재하지 않는 스킬입니다.");
        }

        if (!skillValidator.isSkillOwner(skillId, AuthUtil.getCurrentMemberId())) {
            throw new AccessDeniedException("권한이 없습니다.");
        }

        List<Skill> requiredSkills = skillRequiredSkillRepository.findAllBySkillId(skillId);

        return requiredSkills.stream()
                .map(SkillResponseDto::from)
                .toList();
    }

    public List<MonsterResponseDto> getSkillRequiredMonsters(Long skillId) {
        if (!skillRepository.existsById(skillId)) {
            throw new EntityNotFoundException("존재하지 않는 스킬입니다.");
        }

        if (!skillValidator.isSkillOwner(skillId, AuthUtil.getCurrentMemberId())) {
            throw new AccessDeniedException("권한이 없습니다.");
        }

        List<Monster> requiredMonsters = skillRequiredMonsterRepository.findAllBySkillId(skillId);

        return requiredMonsters.stream()
                .map(MonsterResponseDto::from)
                .toList();
    }

    public void createSkillRequiredQuest(Long skillId, Long requiredQuestId) {
        Skill skill = skillRepository.findById(skillId)
                .orElseThrow(() -> new EntityNotFoundException("존재하지 않는 스킬입니다."));

        if (!skillValidator.isSkillOwner(skillId, AuthUtil.getCurrentMemberId())) {
            throw new AccessDeniedException("권한이 없습니다.");
        }

        if (skillRequiredQuestRepository.existsBySkill_IdAndRequiredQuest_Id(skillId, requiredQuestId)) {
            throw new DuplicateDataException("이미 등록된 퀘스트입니다.");
        }

        Quest requiredQuest = questRepository.findById(requiredQuestId)
                .orElseThrow(() -> new EntityNotFoundException("존재하지 않는 퀘스트입니다."));

        SkillRequiredQuest skillRequiredQuest = SkillRequiredQuest.builder()
                .skill(skill)
                .quest(requiredQuest)
                .build();

        skillRequiredQuestRepository.save(skillRequiredQuest);

    }

    @Transactional
    public void deleteSkillRequiredQuest(Long skillId, Long requiredQuestId) {
        if (!skillRepository.existsById(skillId)) {
            throw new EntityNotFoundException("존재하지 않는 스킬입니다.");
        }

        if (!skillValidator.isSkillOwner(skillId, AuthUtil.getCurrentMemberId())) {
            throw new AccessDeniedException("권한이 없습니다.");
        }

        if (!skillRequiredQuestRepository.existsBySkill_IdAndRequiredQuest_Id(skillId, requiredQuestId)) {
            throw new EntityNotFoundException("등록되지 않은 퀘스트입니다.");
        }

        skillRequiredQuestRepository.deleteBySkill_IdAndRequiredQuest_Id(skillId, requiredQuestId);
    }

    public void createSkillRequiredSkill(Long skillId, Long requiredSkillId) {
        Skill skill = skillRepository.findById(skillId)
                .orElseThrow(() -> new EntityNotFoundException("존재하지 않는 스킬입니다."));

        if (!skillValidator.isSkillOwner(skillId, AuthUtil.getCurrentMemberId())) {
            throw new AccessDeniedException("권한이 없습니다.");
        }

        if (skillRequiredSkillRepository.existsBySkillIdAndRequiredSkillId(skillId, requiredSkillId)) {
            throw new DuplicateDataException("이미 등록된 스킬입니다.");
        }
        if (skillId == requiredSkillId) {
            throw new SelfReferenceException("자기 자신을 요구하는 스킬은 등록할 수 없습니다.");
        }
        
        // 순환 참조 확인
        if (skillValidator.hasCircularDependency(requiredSkillId, skillId)) {
            throw new CyclicReferenceException("순환 참조가 발생합니다. A→B→C→A와 같은 참조 구조는 불가능합니다.");
        }

        Skill requiredSkill = skillRepository.findById(requiredSkillId)
                .orElseThrow(() -> new EntityNotFoundException("존재하지 않는 스킬입니다."));

        SkillRequiredSkill skillRequiredSkill = SkillRequiredSkill.builder()
                .skill(skill)
                .requiredSkill(requiredSkill)
                .build();

        skillRequiredSkillRepository.save(skillRequiredSkill);
    }
    
    @Transactional
    public void deleteSkillRequiredSkill(Long skillId, Long requiredSkillId) {
        if (!skillRepository.existsById(skillId)) {
            throw new EntityNotFoundException("존재하지 않는 스킬입니다.");
        }

        if (!skillValidator.isSkillOwner(skillId, AuthUtil.getCurrentMemberId())) {
            throw new AccessDeniedException("권한이 없습니다.");
        }
        
        if (!skillRequiredSkillRepository.existsBySkillIdAndRequiredSkillId(skillId, requiredSkillId)) {
            throw new EntityNotFoundException("등록되지 않은 스킬입니다.");
        }
        
        skillRequiredSkillRepository.deleteBySkillIdAndRequiredSkillId(skillId, requiredSkillId);
    }

    public void createSkillRequiredMonster(Long skillId, Long requiredMonsterId) {
        Skill skill = skillRepository.findById(skillId)
                .orElseThrow(() -> new EntityNotFoundException("존재하지 않는 스킬입니다."));

        if (!skillValidator.isSkillOwner(skillId, AuthUtil.getCurrentMemberId())) {
            throw new AccessDeniedException("권한이 없습니다.");
        }

        if (skillRequiredMonsterRepository.existsBySkill_IdAndRequiredMonster_Id(skillId, requiredMonsterId)) {
            throw new DuplicateDataException("이미 등록된 몬스터입니다.");
        }

        Monster requiredMonster = monsterRepository.findById(requiredMonsterId)
                .orElseThrow(() -> new EntityNotFoundException("존재하지 않는 몬스터입니다."));

        SkillRequiredMonster skillRequiredMonster = SkillRequiredMonster.builder()
                .skill(skill)
                .monster(requiredMonster)
                .build();

        skillRequiredMonsterRepository.save(skillRequiredMonster);
    }

    @Transactional
    public void deleteSkillRequiredMonster(Long skillId, Long requiredMonsterId) {
        if (!skillRepository.existsById(skillId)) {
            throw new EntityNotFoundException("존재하지 않는 스킬입니다.");
        }

        if (!skillValidator.isSkillOwner(skillId, AuthUtil.getCurrentMemberId())) {
            throw new AccessDeniedException("권한이 없습니다.");
        }

        if (!skillRequiredMonsterRepository.existsBySkill_IdAndRequiredMonster_Id(skillId, requiredMonsterId)) {
            throw new EntityNotFoundException("등록되지 않은 몬스터입니다.");
        }

        skillRequiredMonsterRepository.deleteBySkill_IdAndRequiredMonster_Id(skillId, requiredMonsterId);
    }
}
