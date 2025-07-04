package com.devquest.domain.monster.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.devquest.domain.monster.dto.responseDto.QuizResponseDto;

@RestController
@RequestMapping("/api/monsters")
public class MonsterQuizController {

    // 몬스터별 퀴즈 조회
    @GetMapping("/{monsterId}/quizzes")
    public ResponseEntity<List<QuizResponseDto>> getQuizzesByMonsterId(
            @PathVariable(name = "monsterId") Long monsterId) {
        return null;
    }

    // 몬스터에 퀴즈 등록
    @PostMapping("/{monsterId}/quizzes/{quizId}")
    public ResponseEntity<Void> registerQuizForMonster(
            @PathVariable(name = "monsterId") Long monsterId,
            @PathVariable(name = "quizId") Long quizId) {
        return null;
    }

    // 몬스터에 퀴즈 삭제
    @GetMapping("/{monsterId}/quizzes/{quizId}")
    public ResponseEntity<Void> deleteQuizForMonster(
            @PathVariable(name = "monsterId") Long monsterId,
            @PathVariable(name = "quizId") Long quizId) {
        return null;
    }
}
