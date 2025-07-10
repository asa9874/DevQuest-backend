package com.devquest.domain.monster.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.devquest.domain.monster.dto.responseDto.QuizWithOutAnswerResponseDto;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "몬스터-퀴즈 연결 API", description = "몬스터와 퀴즈 연결 관련 API")
public interface MonsterQuizApi {

    @Operation(summary = "몬스터별 퀴즈 목록 조회 (전체 공개)", description = "특정 몬스터에 연결된 퀴즈 목록을 조회합니다. (정답 미포함)")
    @GetMapping("/{monsterId}/quizzes")
    ResponseEntity<List<QuizWithOutAnswerResponseDto>> getQuizzesByMonsterId(
            @Parameter(description = "몬스터 ID", example = "1") @PathVariable(name = "monsterId") Long monsterId);

    @Operation(summary = "몬스터에 퀴즈 등록 (몬스터 주인/어드민)", description = "특정 몬스터에 퀴즈를 연결합니다.")
    @PostMapping("/{monsterId}/quizzes/{quizId}")
    ResponseEntity<Void> registerQuizForMonster(
            @Parameter(description = "몬스터 ID", example = "1") @PathVariable(name = "monsterId") Long monsterId,
            @Parameter(description = "퀴즈 ID", example = "1") @PathVariable(name = "quizId") Long quizId);

    @Operation(summary = "몬스터에서 퀴즈 삭제 (몬스터 주인/어드민)", description = "특정 몬스터에서 퀴즈 연결을 해제합니다.")
    @DeleteMapping("/{monsterId}/quizzes/{quizId}")
    ResponseEntity<Void> deleteQuizForMonster(
            @Parameter(description = "몬스터 ID", example = "1") @PathVariable(name = "monsterId") Long monsterId,
            @Parameter(description = "퀴즈 ID", example = "1") @PathVariable(name = "quizId") Long quizId);
}
