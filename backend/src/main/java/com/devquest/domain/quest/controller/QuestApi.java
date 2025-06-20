package com.devquest.domain.quest.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import com.devquest.domain.quest.dto.requestDto.QuestCreateRequestDto;
import com.devquest.domain.quest.dto.responseDto.QuestResponseDto;
import com.devquest.global.jwt.CustomUserDetails;

public interface QuestApi {

    @PostMapping
    public ResponseEntity<QuestResponseDto> createQuest(
            @RequestBody QuestCreateRequestDto requestDto,
            @AuthenticationPrincipal CustomUserDetails member);

    @GetMapping
    public ResponseEntity<List<QuestResponseDto>> getAllQuests(
            @AuthenticationPrincipal CustomUserDetails member);

    @GetMapping("/{questId}")
    public ResponseEntity<QuestResponseDto> getQuest(
            @PathVariable(name = "questId") Long questId);

    @GetMapping("/search")
    public ResponseEntity<List<QuestResponseDto>> searchQuests(
            @RequestParam(required = false) String title,
            @RequestParam(required = false) Long creatorId,
            @RequestParam(required = false) Boolean completed);

    @GetMapping("/member/{memberId}")
    public ResponseEntity<List<QuestResponseDto>> getQuestsByMemberId(
            @PathVariable(name = "memberId") Long memberId,
            @RequestParam(required = false) Boolean completed,
            @AuthenticationPrincipal CustomUserDetails member);

    @GetMapping("/member/{memberId}/completed")
    public ResponseEntity<QuestResponseDto> updateQuest(
            @PathVariable(name = "questId") Long questId,
            @RequestBody QuestCreateRequestDto requestDto,
            @AuthenticationPrincipal CustomUserDetails member);

    @DeleteMapping("/{questId}")
    public ResponseEntity<Void> deleteQuest(
            @PathVariable(name = "questId") Long questId,
            @AuthenticationPrincipal CustomUserDetails member);

    @PostMapping("/{questId}/complete")
    public ResponseEntity<Void> completeQuest(
            @PathVariable(name = "questId") Long questId,
            @AuthenticationPrincipal CustomUserDetails member);

    @PostMapping("/{questId}/like")
    public ResponseEntity<Void> likeQuest(
            @PathVariable(name = "questId") Long questId,
            @AuthenticationPrincipal CustomUserDetails member);

    @DeleteMapping("/{questId}/like")
    public ResponseEntity<Void> unlikeQuest(
            @PathVariable(name = "questId") Long questId,
            @AuthenticationPrincipal CustomUserDetails member);

    @GetMapping("/liked")
    public ResponseEntity<List<QuestResponseDto>> getLikedQuests(
            @AuthenticationPrincipal CustomUserDetails member);
}
