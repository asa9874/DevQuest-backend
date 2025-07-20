package com.devquest.domain.skill.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.devquest.domain.monster.dto.responseDto.MonsterResponseDto;
import com.devquest.domain.quest.dto.responseDto.QuestResponseDto;
import com.devquest.domain.skill.dto.responseDto.SkillResponseDto;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "스킬 사전 조건 API", description = "스킬 습득을 위한 사전 조건(스킬, 몬스터, 퀘스트) 관련 API")
public interface SkillRequiredApi {

    @Operation(summary = "스킬 사전 조건 퀘스트 조회", description = "특정 스킬의 사전 조건 퀘스트 목록을 조회합니다.")
    @GetMapping("/{skillId}/required/quests")
    ResponseEntity<List<QuestResponseDto>> getSkillRequiredQuests(
            @Parameter(description = "스킬 ID", example = "1") @PathVariable(name = "skillId") Long skillId);

    @Operation(summary = "스킬 사전 조건 스킬 조회", description = "특정 스킬의 사전 조건 스킬 목록을 조회합니다.")
    @GetMapping("/{skillId}/required/skills")
    ResponseEntity<List<SkillResponseDto>> getSkillRequiredSkills(
            @Parameter(description = "스킬 ID", example = "1") @PathVariable(name = "skillId") Long skillId);

    @Operation(summary = "스킬 사전 조건 몬스터 조회", description = "특정 스킬의 사전 조건 몬스터 목록을 조회합니다.")
    @GetMapping("/{skillId}/required/monsters")
    ResponseEntity<List<MonsterResponseDto>> getSkillRequiredMonsters(
            @Parameter(description = "스킬 ID", example = "1") @PathVariable(name = "skillId") Long skillId);

    @Operation(summary = "스킬 사전 조건 퀘스트 추가(스킬주인/어드민)", description = "특정 스킬을 습득하기 위한 사전 조건 퀘스트를 추가합니다. ")
    @PostMapping("/{skillId}/required/quest/{requiredQuestId}")
    ResponseEntity<Void> createSkillRequiredQuest(
            @Parameter(description = "스킬 ID", example = "1") @PathVariable(name = "skillId") Long skillId,
            @Parameter(description = "사전 조건 퀘스트 ID", example = "2") @PathVariable(name = "requiredQuestId") Long requiredQuestId);

    @Operation(summary = "스킬 사전 조건 퀘스트 삭제(스킬주인/어드민)", description = "특정 스킬의 사전 조건 퀘스트를 삭제합니다. ")
    @DeleteMapping("/{skillId}/required/quest/{requiredQuestId}")
    ResponseEntity<Void> deleteSkillRequiredQuest(
            @Parameter(description = "스킬 ID", example = "1") @PathVariable(name = "skillId") Long skillId,
            @Parameter(description = "사전 조건 퀘스트 ID", example = "2") @PathVariable(name = "requiredQuestId") Long requiredQuestId);

    @Operation(summary = "스킬 사전 조건 스킬 추가(스킬주인/어드민)", description = "특정 스킬을 습득하기 위한 사전 조건 스킬을 추가합니다. ")
    @PostMapping("/{skillId}/required/skill/{requiredSkillId}")
    ResponseEntity<Void> createSkillRequiredSkill(
            @Parameter(description = "스킬 ID", example = "1") @PathVariable(name = "skillId") Long skillId,
            @Parameter(description = "사전 조건 스킬 ID", example = "2") @PathVariable(name = "requiredSkillId") Long requiredSkillId);

    @Operation(summary = "스킬 사전 조건 스킬 삭제(스킬주인/어드민)", description = "특정 스킬의 사전 조건 스킬을 삭제합니다. ")
    @DeleteMapping("/{skillId}/required/skill/{requiredSkillId}")
    ResponseEntity<Void> deleteSkillRequiredSkill(
            @Parameter(description = "스킬 ID", example = "1") @PathVariable(name = "skillId") Long skillId,
            @Parameter(description = "사전 조건 스킬 ID", example = "2") @PathVariable(name = "requiredSkillId") Long requiredSkillId);

    @Operation(summary = "스킬 사전 조건 몬스터 추가(스킬주인/어드민)", description = "특정 스킬을 습득하기 위한 사전 조건 몬스터를 추가합니다. ")
    @PostMapping("/{skillId}/required/monster/{requiredMonsterId}")
    ResponseEntity<Void> createSkillRequiredMonster(
            @Parameter(description = "스킬 ID", example = "1") @PathVariable(name = "skillId") Long skillId,
            @Parameter(description = "사전 조건 몬스터 ID", example = "2") @PathVariable(name = "requiredMonsterId") Long requiredMonsterId);

    @Operation(summary = "스킬 사전 조건 몬스터 삭제(스킬주인/어드민)", description = "특정 스킬의 사전 조건 몬스터를 삭제합니다. ")
    @DeleteMapping("/{skillId}/required/monster/{requiredMonsterId}")
    ResponseEntity<Void> deleteSkillRequiredMonster(
            @Parameter(description = "스킬 ID", example = "1") @PathVariable(name = "skillId") Long skillId,
            @Parameter(description = "사전 조건 몬스터 ID", example = "2") @PathVariable(name = "requiredMonsterId") Long requiredMonsterId);
}
