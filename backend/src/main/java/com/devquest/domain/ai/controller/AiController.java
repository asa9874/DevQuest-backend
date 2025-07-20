package com.devquest.domain.ai.controller;

import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.devquest.domain.ai.service.AiService;
import com.devquest.global.jwt.CustomUserDetails;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/ai")
public class AiController implements AiApi {

    private final AiService aiService;

    @PostMapping("/quest")
    public ResponseEntity<Map<String, Object>> generateQuest(
            @RequestParam String task_input,
            @AuthenticationPrincipal CustomUserDetails userDetails) {
        return ResponseEntity.ok().body(aiService.generateQuest(task_input));
    }

    @PostMapping("/quiz")
    public ResponseEntity<Map<String, Object>> generateQuiz(
            @RequestParam String task_input,
            @AuthenticationPrincipal CustomUserDetails userDetails) {
        return ResponseEntity.ok().body(aiService.generateQuiz(task_input));
    }

    @PostMapping("/quiz/batch")
    public ResponseEntity<List<Map<String, Object>>> generateQuizBatch(
            @RequestParam String name,
            @RequestParam String description,
            @RequestParam(defaultValue = "1") int number,
            @AuthenticationPrincipal CustomUserDetails userDetails) {
        return ResponseEntity.ok().body(aiService.generateQuizBatch(name, description, number));
    }

    @GetMapping("/guild/search")
    public ResponseEntity<List<Map<String, Object>>> searchGuilds(
            @RequestParam(required = false) String query,
            @AuthenticationPrincipal CustomUserDetails userDetails) {
        return ResponseEntity.ok().body(aiService.searchGuilds(query));
    }
}
