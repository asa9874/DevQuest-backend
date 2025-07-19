package com.devquest.domain.ai.controller;

import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.devquest.global.jwt.CustomUserDetails;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "AI API", description = "AI 기반 콘텐츠 생성 및 검색 API")
public interface AiApi {

    @Operation(summary = "AI 퀘스트 생성 (유저/어드민)", description = "AI를 사용하여 새로운 퀘스트를 생성합니다.")
    @PostMapping("/quest")
    ResponseEntity<Map<String, Object>> generateQuest(
            @Parameter(description = "퀘스트 생성을 위한 입력 텍스트", example = "Java 프로그래밍 기초") 
            @RequestParam String task_input,
            @Parameter(description = "로그인 사용자 정보", hidden = true) 
            @AuthenticationPrincipal CustomUserDetails userDetails);

    @Operation(summary = "AI 퀴즈 생성 (유저/어드민)", description = "AI를 사용하여 새로운 퀴즈를 생성합니다.")
    @PostMapping("/quiz")
    ResponseEntity<Map<String, Object>> generateQuiz(
            @Parameter(description = "퀴즈 생성을 위한 입력 텍스트", example = "Spring Boot 개념") 
            @RequestParam String task_input,
            @Parameter(description = "로그인 사용자 정보", hidden = true) 
            @AuthenticationPrincipal CustomUserDetails userDetails);

    @Operation(summary = "AI 퀴즈 배치 생성 (유저/어드민)", description = "AI를 사용하여 여러 개의 퀴즈를 한 번에 생성합니다.")
    @PostMapping("/quiz/batch")
    ResponseEntity<List<Map<String, Object>>> generateQuizBatch(
            @Parameter(description = "퀴즈 주제명", example = "React Hooks") 
            @RequestParam String name,
            @Parameter(description = "퀴즈 주제 설명", example = "React Hooks의 기본 개념과 사용법") 
            @RequestParam String description,
            @Parameter(description = "생성할 퀴즈 개수", example = "5") 
            @RequestParam(defaultValue = "1") int number,
            @Parameter(description = "로그인 사용자 정보", hidden = true) 
            @AuthenticationPrincipal CustomUserDetails userDetails);

    @Operation(summary = "AI 길드 검색 (유저/어드민)", description = "AI를 사용하여 관련 길드를 검색합니다.")
    @GetMapping("/guild/search")
    ResponseEntity<List<Map<String, Object>>> searchGuilds(
            @Parameter(description = "검색할 길드 관련 키워드 (선택사항)", example = "프론트엔드") 
            @RequestParam(required = false) String query,
            @Parameter(description = "로그인 사용자 정보", hidden = true) 
            @AuthenticationPrincipal CustomUserDetails userDetails);
}
