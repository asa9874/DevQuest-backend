package com.devquest.domain.monster.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.devquest.domain.monster.dto.requestDto.MonsterCreateRequestDto;
import com.devquest.domain.monster.dto.requestDto.MonsterUpdateRequestDto;
import com.devquest.domain.monster.dto.responseDto.MonsterResponseDto;
import com.devquest.global.jwt.CustomUserDetails;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;

import jakarta.validation.Valid;

@Tag(name = "몬스터 API", description = "몬스터 관련 API")
public interface MonsterApi {

    @Operation(summary = "전체 몬스터 목록 조회 (전체 공개)", description = "모든 몬스터의 정보를 조회합니다.")
    @GetMapping
    ResponseEntity<List<MonsterResponseDto>> getAllMonsters();

    @Operation(summary = "몬스터 상세 조회 (전체 공개)", description = "특정 몬스터의 상세 정보를 조회합니다.")
    @GetMapping("/{monsterId}")
    ResponseEntity<MonsterResponseDto> getMonsterById(
            @Parameter(description = "몬스터 ID", example = "1") @PathVariable(name = "monsterId") Long monsterId);

    @Operation(summary = "몬스터 생성 (유저/어드민)", description = "새로운 몬스터를 생성합니다. (로그인 필요)")
    @PostMapping
    ResponseEntity<Void> createMonster(
            @Parameter(description = "몬스터 생성 요청 DTO") @Valid @RequestBody MonsterCreateRequestDto requestDto,
            @Parameter(description = "로그인 사용자 정보", hidden = true) @AuthenticationPrincipal CustomUserDetails member);

    @Operation(summary = "몬스터 정보 수정 (작성자/어드민)", description = "특정 몬스터의 정보를 수정합니다.")
    @PutMapping("/{monsterId}")
    ResponseEntity<Void> updateMonster(
            @Parameter(description = "몬스터 ID", example = "1") @PathVariable(name = "monsterId") Long monsterId,
            @Parameter(description = "몬스터 정보 수정 DTO") @Valid @RequestBody MonsterUpdateRequestDto requestDto,
            @Parameter(description = "로그인 사용자 정보", hidden = true) @AuthenticationPrincipal CustomUserDetails member);

    @Operation(summary = "몬스터 삭제 (작성자/어드민)", description = "특정 몬스터를 삭제합니다.")
    @DeleteMapping("/{monsterId}")
    ResponseEntity<Void> deleteMonster(
            @Parameter(description = "몬스터 ID", example = "1") @PathVariable(name = "monsterId") Long monsterId,
            @Parameter(description = "로그인 사용자 정보", hidden = true) @AuthenticationPrincipal CustomUserDetails member);
}
