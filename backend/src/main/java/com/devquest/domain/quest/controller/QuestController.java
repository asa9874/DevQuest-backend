package com.devquest.domain.quest.controller;

import java.net.http.HttpClient;
import java.util.List;

import org.springframework.boot.autoconfigure.graphql.GraphQlProperties.Http;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.devquest.domain.quest.dto.requestDto.QuestCreateRequestDto;
import com.devquest.domain.quest.dto.responseDto.QuestResponseDto;
import com.devquest.domain.quest.service.QuestService;
import com.devquest.global.jwt.CustomUserDetails;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/quests")
public class QuestController {
    private final QuestService questService;

    @PostMapping
    public ResponseEntity<Void> createQuest(
            @RequestBody QuestCreateRequestDto requestDto,
            @AuthenticationPrincipal CustomUserDetails member) {
        questService.createQuest(requestDto, member.getId());
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping
    public ResponseEntity<List<QuestResponseDto>> getAllQuests(
            @AuthenticationPrincipal CustomUserDetails member){
        return ResponseEntity.ok(null);
    }

    @GetMapping("/{questId}")
    public ResponseEntity<QuestResponseDto> getQuest(
            @PathVariable(name = "questId") Long questId){
        return ResponseEntity.ok(null);
    }

    @GetMapping("/search")
    public ResponseEntity<List<QuestResponseDto>> searchQuests(
            @RequestParam(required = false) String title,
            @RequestParam(required = false) Long creatorId,
            @RequestParam(required = false) Boolean completed){
        return ResponseEntity.ok(null);
    }

    @GetMapping("/member/{memberId}")
    public ResponseEntity<List<QuestResponseDto>> getQuestsByMemberId(
            @PathVariable(name = "memberId") Long memberId,
            @RequestParam(required = false) Boolean completed,
            @AuthenticationPrincipal CustomUserDetails member){
        return ResponseEntity.ok(null);
    }

    @GetMapping("/member/{memberId}/completed")
    public ResponseEntity<QuestResponseDto> updateQuest(
            @PathVariable(name = "questId") Long questId,
            @RequestBody QuestCreateRequestDto requestDto,
            @AuthenticationPrincipal CustomUserDetails member){
        return ResponseEntity.ok(null);
    }

    @DeleteMapping("/{questId}")
    public ResponseEntity<Void> deleteQuest(
            @PathVariable(name = "questId") Long questId,
            @AuthenticationPrincipal CustomUserDetails member){
        return ResponseEntity.ok(null);
    }

    @PostMapping("/{questId}/complete")
    public ResponseEntity<Void> completeQuest(
            @PathVariable(name = "questId") Long questId,
            @AuthenticationPrincipal CustomUserDetails member){
        return ResponseEntity.ok(null);
    }

    @PostMapping("/{questId}/like")
    public ResponseEntity<Void> likeQuest(
            @PathVariable(name = "questId") Long questId,
            @AuthenticationPrincipal CustomUserDetails member){
        return ResponseEntity.ok(null);
    }

    @DeleteMapping("/{questId}/like")
    public ResponseEntity<Void> unlikeQuest(
            @PathVariable(name = "questId") Long questId,
            @AuthenticationPrincipal CustomUserDetails member){
        return ResponseEntity.ok(null);
    }

    @GetMapping("/liked")
    public ResponseEntity<List<QuestResponseDto>> getLikedQuests(
            @AuthenticationPrincipal CustomUserDetails member){
        return ResponseEntity.ok(null);
    }
}
