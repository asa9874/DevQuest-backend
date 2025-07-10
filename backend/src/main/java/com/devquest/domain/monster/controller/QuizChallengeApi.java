package com.devquest.domain.monster.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.devquest.domain.monster.dto.requestDto.QuizChallengeSolveRequestDto;
import com.devquest.global.jwt.CustomUserDetails;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;

import jakarta.validation.Valid;

@Tag(name = "퀴즈 챌린지 API", description = "퀴즈 도전 관련 API")
public interface QuizChallengeApi {

    @Operation(summary = "퀴즈 풀기 (회원)", description = "특정 퀴즈 챌린지에 대한 답변을 제출합니다.")
    @PostMapping("/challenges/{quizChallengeId}")
    ResponseEntity<Void> solveQuizChallenge(
            @Parameter(description = "퀴즈 챌린지 ID", example = "1") @PathVariable(name = "quizChallengeId") Long quizChallengeId,
            @Parameter(description = "퀴즈 답변 제출 DTO") @Valid @RequestBody QuizChallengeSolveRequestDto requestDto,
            @Parameter(description = "로그인 사용자 정보", hidden = true) @AuthenticationPrincipal CustomUserDetails member);
}
