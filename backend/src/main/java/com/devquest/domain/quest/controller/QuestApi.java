package com.devquest.domain.quest.controller;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import com.devquest.domain.quest.dto.requestDto.QuestCreateRequestDto;
import com.devquest.domain.quest.dto.requestDto.QuestUpdateRequestDto;
import com.devquest.domain.quest.dto.responseDto.QuestResponseDto;
import com.devquest.global.jwt.CustomUserDetails;

public interface QuestApi {

    @PostMapping
    ResponseEntity<Void> createQuest(
            @RequestBody QuestCreateRequestDto requestDto,
            @AuthenticationPrincipal CustomUserDetails member);

    @GetMapping
    ResponseEntity<List<QuestResponseDto>> getAllQuests();

    @GetMapping("/{questId}")
    ResponseEntity<QuestResponseDto> getQuest(
            @PathVariable(name = "questId") Long questId);

    @GetMapping("/search")
    ResponseEntity<Page<QuestResponseDto>> searchQuests(
            @RequestParam(required = false, name = "title") String title,
            @RequestParam(required = false, name = "creatorName") String creatorName,
            Pageable pageable);

    @GetMapping("/member/{memberId}")
    ResponseEntity<List<QuestResponseDto>> getQuestsByMemberId(
            @PathVariable(name = "memberId") Long memberId,
            @RequestParam(required = false) Boolean completed,
            @RequestParam(required = false) Boolean liked,
            @RequestParam(required = false) String title,
            Pageable pageable);

    @GetMapping("/member/{memberId}/completed")
    ResponseEntity<QuestResponseDto> updateQuest(
            @PathVariable(name = "questId") Long questId,
            @RequestBody QuestUpdateRequestDto requestDto);

    @DeleteMapping("/{questId}")
    ResponseEntity<Void> deleteQuest(
            @PathVariable(name = "questId") Long questId);

    @PostMapping("/{questId}/complete")
    ResponseEntity<Void> completeQuest(
            @PathVariable(name = "questId") Long questId);

    @PostMapping("/{questId}/like")
    ResponseEntity<Void> likeQuest(
            @PathVariable(name = "questId") Long questId,
            @AuthenticationPrincipal CustomUserDetails member);

    @DeleteMapping("/{questId}/like")
    ResponseEntity<Void> unlikeQuest(
            @PathVariable(name = "questId") Long questId,
            @AuthenticationPrincipal CustomUserDetails member);

    @GetMapping("/liked")
    ResponseEntity<List<QuestResponseDto>> getLikedQuests(
            @AuthenticationPrincipal CustomUserDetails member);
}
