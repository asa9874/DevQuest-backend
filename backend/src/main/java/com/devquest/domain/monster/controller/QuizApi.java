package com.devquest.domain.monster.controller;

import java.util.List;

import jakarta.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.devquest.domain.monster.dto.requestDto.QuizCreateRequestDto;
import com.devquest.domain.monster.dto.requestDto.QuizUpdateRequestDto;
import com.devquest.domain.monster.dto.responseDto.QuizResponseDto;
import com.devquest.domain.monster.dto.responseDto.QuizWithOutAnswerResponseDto;
import com.devquest.global.jwt.CustomUserDetails;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "퀴즈 API", description = "퀴즈 관련 API")
public interface QuizApi {

    @Operation(summary = "전체 퀴즈 목록 조회 (어드민)", description = "모든 퀴즈의 정보를 조회합니다. (정답 포함)")
    @GetMapping
    ResponseEntity<List<QuizResponseDto>> getAllQuizzes();

    @Operation(summary = "퀴즈 상세 조회 (어드민)", description = "특정 퀴즈의 상세 정보를 조회합니다. (정답 포함)")
    @GetMapping("/{quizId}")
    ResponseEntity<QuizResponseDto> getQuizById(
            @Parameter(description = "퀴즈 ID", example = "1") @PathVariable(name = "quizId") Long quizId);

    @Operation(summary = "퀴즈 조회 - 정답 미포함 (전체 공개)", description = "특정 퀴즈 정보를 조회합니다. (정답 미포함)")
    @GetMapping("/{quizId}/without-answer")
    ResponseEntity<QuizWithOutAnswerResponseDto> getQuizWithoutAnswer(
            @Parameter(description = "퀴즈 ID", example = "1") @PathVariable(name = "quizId") Long quizId);

    @Operation(summary = "퀴즈 생성 (회원/어드민)", description = "새로운 퀴즈를 생성합니다.")
    @PostMapping
    ResponseEntity<Void> createQuiz(
            @Parameter(description = "퀴즈 생성 요청 DTO") @Valid @RequestBody QuizCreateRequestDto requestDto,
            @Parameter(description = "로그인 사용자 정보", hidden = true) @AuthenticationPrincipal CustomUserDetails member);

    @Operation(summary = "퀴즈 정보 수정 (작성자/어드민)", description = "특정 퀴즈의 정보를 수정합니다.")
    @PutMapping("/{quizId}")
    ResponseEntity<Void> updateQuiz(
            @Parameter(description = "퀴즈 ID", example = "1") @PathVariable(name = "quizId") Long quizId,
            @Parameter(description = "퀴즈 정보 수정 DTO") @Valid @RequestBody QuizUpdateRequestDto requestDto,
            @Parameter(description = "로그인 사용자 정보", hidden = true) @AuthenticationPrincipal CustomUserDetails member);

    @Operation(summary = "퀴즈 삭제 (작성자/어드민)", description = "특정 퀴즈를 삭제합니다.")
    @DeleteMapping("/{quizId}")
    ResponseEntity<Void> deleteQuiz(
            @Parameter(description = "퀴즈 ID", example = "1") @PathVariable(name = "quizId") Long quizId,
            @Parameter(description = "로그인 사용자 정보", hidden = true) @AuthenticationPrincipal CustomUserDetails member);
}
