package com.devquest.domain.skill.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.devquest.domain.skill.dto.responseDto.MemberSkillResponseDto;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "멤버 스킬 API", description = "멤버의 스킬 습득, 조회, 삭제 API")
public interface MemberSkillApi {

    @Operation(summary = "멤버의 특정 스킬 조회", description = "특정 멤버가 습득한 특정 스킬의 정보를 조회합니다.")
    @GetMapping("/{memberId}/skills/{skillId}")
    ResponseEntity<MemberSkillResponseDto> getMemberSkill(
            @Parameter(description = "멤버 ID", example = "1") @PathVariable(name = "memberId") Long memberId,
            @Parameter(description = "스킬 ID", example = "1") @PathVariable(name = "skillId") Long skillId);

    @Operation(summary = "멤버의 모든 스킬 조회", description = "특정 멤버가 습득한 모든 스킬의 정보를 조회합니다.")
    @GetMapping("/{memberId}/skills")
    ResponseEntity<List<MemberSkillResponseDto>> getMemberSkills(
            @Parameter(description = "멤버 ID", example = "1") @PathVariable(name = "memberId") Long memberId);

    @Operation(summary = "멤버 스킬 습득 (본인/어드민)", description = "특정 멤버가 특정 스킬을 습득합니다. 사전 조건(스킬, 몬스터, 퀘스트)을 만족해야 합니다.")
    @PostMapping("/{memberId}/skills/{skillId}")
    ResponseEntity<Void> createMemberSkill(
            @Parameter(description = "멤버 ID", example = "1") @PathVariable(name = "memberId") Long memberId,
            @Parameter(description = "스킬 ID", example = "1") @PathVariable(name = "skillId") Long skillId);

    @Operation(summary = "멤버 스킬 삭제 (본인/어드민)", description = "특정 멤버가 습득한 특정 스킬을 삭제합니다.")
    @DeleteMapping("/{memberId}/skills/{skillId}")
    ResponseEntity<Void> deleteMemberSkill(
            @Parameter(description = "멤버 ID", example = "1") @PathVariable(name = "memberId") Long memberId,
            @Parameter(description = "스킬 ID", example = "1") @PathVariable(name = "skillId") Long skillId);
}
