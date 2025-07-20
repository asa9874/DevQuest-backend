package com.devquest.domain.skill.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.devquest.domain.monster.dto.responseDto.MonsterResponseDto;
import com.devquest.domain.quest.dto.responseDto.QuestResponseDto;
import com.devquest.domain.skill.dto.responseDto.SkillResponseDto;
import com.devquest.domain.skill.service.SkillRequiredService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/skills")
public class SkillRequiredController implements SkillRequiredApi {
    private final SkillRequiredService skillRequiredService;

    @GetMapping("/{skillId}/required/quests")
    public ResponseEntity<List<QuestResponseDto>> getSkillRequiredQuests(
            @PathVariable(name = "skillId") Long skillId) {
        List<QuestResponseDto> requiredQuests = skillRequiredService.getSkillRequiredQuests(skillId);
        return ResponseEntity.ok(requiredQuests);
    }

    @GetMapping("/{skillId}/required/skills")
    public ResponseEntity<List<SkillResponseDto>> getSkillRequiredSkills(
            @PathVariable(name = "skillId") Long skillId) {
        List<SkillResponseDto> requiredSkills = skillRequiredService.getSkillRequiredSkills(skillId);
        return ResponseEntity.ok(requiredSkills);
    }

    @GetMapping("/{skillId}/required/monsters")
    public ResponseEntity<List<MonsterResponseDto>> getSkillRequiredMonsters(
            @PathVariable(name = "skillId") Long skillId) {
        List<MonsterResponseDto> requiredMonsters = skillRequiredService.getSkillRequiredMonsters(skillId);
        return ResponseEntity.ok(requiredMonsters);
    }

    @PostMapping("/{skillId}/required/quest/{requiredQuestId}")
    public ResponseEntity<Void> createSkillRequiredQuest(
            @PathVariable(name = "skillId") Long skillId,
            @PathVariable(name = "requiredQuestId") Long requiredQuestId) {
        skillRequiredService.createSkillRequiredQuest(skillId, requiredQuestId);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @DeleteMapping("/{skillId}/required/quest/{requiredQuestId}")
    public ResponseEntity<Void> deleteSkillRequiredQuest(
            @PathVariable(name = "skillId") Long skillId,
            @PathVariable(name = "requiredQuestId") Long requiredQuestId) {
        skillRequiredService.deleteSkillRequiredQuest(skillId, requiredQuestId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @PostMapping("/{skillId}/required/skill/{requiredSkillId}")
    public ResponseEntity<Void> createSkillRequiredSkill(
            @PathVariable(name = "skillId") Long skillId,
            @PathVariable(name = "requiredSkillId") Long requiredSkillId) {
        skillRequiredService.createSkillRequiredSkill(skillId, requiredSkillId);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @DeleteMapping("/{skillId}/required/skill/{requiredSkillId}")
    public ResponseEntity<Void> deleteSkillRequiredSkill(
            @PathVariable(name = "skillId") Long skillId,
            @PathVariable(name = "requiredSkillId") Long requiredSkillId) {
        skillRequiredService.deleteSkillRequiredSkill(skillId, requiredSkillId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @PostMapping("/{skillId}/required/monster/{requiredMonsterId}")
    public ResponseEntity<Void> createSkillRequiredMonster(
            @PathVariable(name = "skillId") Long skillId,
            @PathVariable(name = "requiredMonsterId") Long requiredMonsterId) {
        skillRequiredService.createSkillRequiredMonster(skillId, requiredMonsterId);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @DeleteMapping("/{skillId}/required/monster/{requiredMonsterId}")
    public ResponseEntity<Void> deleteSkillRequiredMonster(
            @PathVariable(name = "skillId") Long skillId,
            @PathVariable(name = "requiredMonsterId") Long requiredMonsterId) {
        skillRequiredService.deleteSkillRequiredMonster(skillId, requiredMonsterId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

}
