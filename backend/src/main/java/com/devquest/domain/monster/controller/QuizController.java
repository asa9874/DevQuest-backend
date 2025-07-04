package com.devquest.domain.monster.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
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

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/quizzes")
public class QuizController {

    // 전체 조회
    @GetMapping
    public ResponseEntity<List<QuizResponseDto>> getAllQuizzes() {
        return null;
    }

    // 단일 조회
    @GetMapping("/{quizId}")
    public ResponseEntity<QuizResponseDto> getQuizById(
            @PathVariable(name = "quizId") Long quizId) {
        return null;
    }

    // 등록
    @PostMapping
    public ResponseEntity<Void> createQuiz(
            @Valid @RequestBody QuizCreateRequestDto requestDto) {
        return null;
    }

    // 수정
    @PutMapping("/{quizId}")
    public ResponseEntity<Void> updateQuiz(
            @PathVariable(name = "quizId") Long quizId,
            @Valid @RequestBody QuizUpdateRequestDto requestDto) {
        return null;
    }

    // 삭제
    @DeleteMapping("/{quizId}")
    public ResponseEntity<Void> deleteQuiz(
            @PathVariable(name = "quizId") Long quizId) {
        return null;
    }
}
