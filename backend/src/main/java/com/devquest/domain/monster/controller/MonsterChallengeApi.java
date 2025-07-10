package com.devquest.domain.monster.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.devquest.domain.monster.dto.responseDto.MonsterChallengeResponseDto;
import com.devquest.domain.monster.dto.responseDto.QuizChallengeResponseDto;
import com.devquest.global.jwt.CustomUserDetails;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "몬스터 챌린지 API", description = "몬스터 도전 관련 API")
public interface MonsterChallengeApi {

    @Operation(summary = "몬스터 챌린지 조회 (회원)", description = "특정 몬스터 챌린지의 상세 정보를 조회합니다. (본인의 챌린지만 조회 가능)")
    @GetMapping("/challenges/{challengeId}")
    ResponseEntity<MonsterChallengeResponseDto> getChallengeForMonster(
            @Parameter(description = "챌린지 ID", example = "1") @PathVariable(name = "challengeId") Long challengeId,
            @Parameter(description = "로그인 사용자 정보", hidden = true) @AuthenticationPrincipal CustomUserDetails member);

    @Operation(summary = "몬스터 챌린지 퀴즈 목록 조회 (회원)", description = "특정 몬스터 챌린지의 퀴즈 목록을 조회합니다. (본인의 챌린지만 조회 가능)")
    @GetMapping("/challenges/{challengeId}/quizchallenge")
    ResponseEntity<List<QuizChallengeResponseDto>> getQuizChallengeByMonsterChallengeId(
            @Parameter(description = "챌린지 ID", example = "1") @PathVariable(name = "challengeId") Long challengeId,
            @Parameter(description = "로그인 사용자 정보", hidden = true) @AuthenticationPrincipal CustomUserDetails member);

    @Operation(summary = "몬스터 챌린지 생성 (회원)", description = "특정 몬스터에 대한 챌린지를 생성합니다.")
    @PostMapping("/{monsterId}/challenges")
    ResponseEntity<Void> createChallengeForMonster(
            @Parameter(description = "몬스터 ID", example = "1") @PathVariable(name = "monsterId") Long monsterId,
            @Parameter(description = "로그인 사용자 정보", hidden = true) @AuthenticationPrincipal CustomUserDetails member);

    @Operation(summary = "몬스터 챌린지 완료 (회원)", description = "특정 몬스터 챌린지를 완료 처리합니다. (성공/실패 여부는 퀴즈 정답 개수에 따라 결정)")
    @PostMapping("/challenges/{challengeId}/complete")
    ResponseEntity<Void> completeChallengeForMonster(
            @Parameter(description = "챌린지 ID", example = "1") @PathVariable(name = "challengeId") Long challengeId,
            @Parameter(description = "로그인 사용자 정보", hidden = true) @AuthenticationPrincipal CustomUserDetails member);
}
