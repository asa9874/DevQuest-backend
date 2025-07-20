package com.devquest.domain.monster.controller;

import java.util.List;

import jakarta.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.devquest.domain.monster.dto.requestDto.QuizCreateRequestDto;
import com.devquest.domain.monster.dto.requestDto.QuizUpdateRequestDto;
import com.devquest.domain.monster.dto.responseDto.QuizResponseDto;
import com.devquest.domain.monster.dto.responseDto.QuizWithOutAnswerResponseDto;
import com.devquest.domain.monster.service.QuizService;
import com.devquest.global.jwt.CustomUserDetails;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/quizzes")
public class QuizController implements QuizApi {
    private final QuizService quizService;

    // 전체 조회
    @GetMapping
    public ResponseEntity<List<QuizResponseDto>> getAllQuizzes() {
        List<QuizResponseDto> responseDtos = quizService.getAllQuizzes();
        return ResponseEntity.ok(responseDtos);
    }

    // 단일 조회
    @GetMapping("/{quizId}")
    public ResponseEntity<QuizResponseDto> getQuizById(
            @PathVariable(name = "quizId") Long quizId) {
        QuizResponseDto responseDto = quizService.getQuizById(quizId);
        return ResponseEntity.ok(responseDto);
    }

    // 정답 미포함 조회
    @GetMapping("/{quizId}/without-answer")
    public ResponseEntity<QuizWithOutAnswerResponseDto> getQuizWithoutAnswer(
            @PathVariable(name = "quizId") Long quizId) {
        QuizWithOutAnswerResponseDto responseDto = quizService.getQuizWithoutAnswer(quizId);
        return ResponseEntity.ok(responseDto);
    }

    // 등록
    @PostMapping
    public ResponseEntity<Void> createQuiz(
            @Valid @RequestBody QuizCreateRequestDto requestDto,
            @AuthenticationPrincipal CustomUserDetails member) {
        quizService.createQuiz(requestDto, member.getId());
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    // 수정
    @PutMapping("/{quizId}")
    public ResponseEntity<Void> updateQuiz(
            @PathVariable(name = "quizId") Long quizId,
            @Valid @RequestBody QuizUpdateRequestDto requestDto,
            @AuthenticationPrincipal CustomUserDetails member) {
        quizService.updateQuiz(quizId, requestDto, member.getId());
        return ResponseEntity.ok().build();
    }

    // 삭제
    @DeleteMapping("/{quizId}")
    public ResponseEntity<Void> deleteQuiz(
            @PathVariable(name = "quizId") Long quizId,
            @AuthenticationPrincipal CustomUserDetails member) {
        quizService.deleteQuiz(quizId, member.getId());
        return ResponseEntity.noContent().build();
    }
}
