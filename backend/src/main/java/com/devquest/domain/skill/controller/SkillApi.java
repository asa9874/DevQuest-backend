package com.devquest.domain.skill.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.devquest.domain.skill.dto.requestDto.SkillCreateRequestDto;
import com.devquest.domain.skill.dto.requestDto.SkillUpdateRequestDto;
import com.devquest.domain.skill.dto.responseDto.SkillResponseDto;
import com.devquest.global.jwt.CustomUserDetails;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "스킬 API", description = "스킬 관련 API")
public interface SkillApi {

    @Operation(summary = "전체 스킬 목록 조회", description = "모든 스킬의 정보를 조회합니다.")
    @GetMapping
    ResponseEntity<List<SkillResponseDto>> getSkills();

    @Operation(summary = "스킬 상세 조회", description = "특정 스킬의 상세 정보를 조회합니다.")
    @GetMapping("/{skillId}")
    ResponseEntity<SkillResponseDto> getSkill(
            @Parameter(description = "스킬 ID", example = "1") @PathVariable(name = "skillId") Long skillId);

    @Operation(summary = "스킬 생성 (회원)", description = "새로운 스킬을 생성합니다. ")
    @PostMapping
    ResponseEntity<Void> createSkill(
            @Parameter(description = "스킬 생성 요청 DTO") @RequestBody SkillCreateRequestDto skillCreateRequestDto,
            @Parameter(description = "로그인 사용자 정보", hidden = true) @AuthenticationPrincipal CustomUserDetails member);

    @Operation(summary = "스킬 정보 수정 (주인/어드민)", description = "특정 스킬의 정보를 수정합니다. ")
    @PutMapping("/{skillId}")
    ResponseEntity<Void> updateSkill(
            @Parameter(description = "스킬 ID", example = "1") @PathVariable(name = "skillId") Long skillId,
            @Parameter(description = "스킬 정보 수정 DTO") @RequestBody SkillUpdateRequestDto skillUpdateRequestDto);

    @Operation(summary = "스킬 삭제 (주인/어드민)", description = "특정 스킬을 삭제합니다. ")
    @DeleteMapping("/{skillId}")
    ResponseEntity<Void> deleteSkill(
            @Parameter(description = "스킬 ID", example = "1") @PathVariable(name = "skillId") Long skillId);
}
