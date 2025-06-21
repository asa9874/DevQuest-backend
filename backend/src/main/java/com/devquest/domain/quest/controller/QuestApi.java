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
import com.devquest.domain.quest.dto.responseDto.QuestWithLikeResponseDto;
import com.devquest.global.jwt.CustomUserDetails;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;

public interface QuestApi {
    @Operation(summary = "퀘스트 생성 (자기자신)", description = "새로운 퀘스트를 생성합니다.")
    ResponseEntity<Void> createQuest(
            @Parameter(description = "퀘스트 생성 요청 DTO") @RequestBody QuestCreateRequestDto requestDto,
            @Parameter(description = "로그인 사용자 정보", hidden = true) @AuthenticationPrincipal CustomUserDetails member);

    @Operation(summary = "전체 퀘스트 목록 조회 (어드민)", description = "모든 퀘스트 목록을 조회합니다.")
    ResponseEntity<List<QuestWithLikeResponseDto>> getAllQuests();

    @Operation(summary = "퀘스트 조회 (전체 공개)", description = "특정 퀘스트의 상세 정보를 조회합니다.")
    ResponseEntity<QuestWithLikeResponseDto> getQuest(
            @Parameter(description = "퀘스트 ID", example = "1") @PathVariable(name = "questId") Long questId);

    @Operation(summary = "퀘스트 검색 (전체 공개)", description = "조건에 따라 퀘스트를 검색합니다.")
    ResponseEntity<Page<QuestWithLikeResponseDto>> searchQuests(
            @Parameter(description = "퀘스트 제목") @RequestParam(required = false, name = "title") String title,
            @Parameter(description = "생성자 닉네임") @RequestParam(required = false, name = "creatorName") String creatorName,
            Pageable pageable);

    @Operation(summary = "퀘스트 수정 (자기자신 or 어드민)", description = "퀘스트 정보를 수정합니다.")
    ResponseEntity<QuestResponseDto> updateQuest(
            @Parameter(description = "퀘스트 ID", example = "1") @PathVariable(name = "questId") Long questId,
            @Parameter(description = "퀘스트 수정 요청 DTO") @RequestBody QuestUpdateRequestDto requestDto);

    @Operation(summary = "퀘스트 삭제 (자기자신 or 어드민)", description = "특정 퀘스트를 삭제합니다.")
    ResponseEntity<Void> deleteQuest(
            @Parameter(description = "퀘스트 ID", example = "1") @PathVariable(name = "questId") Long questId);

    @Operation(summary = "퀘스트 좋아요 (자기자신)", description = "특정 퀘스트에 좋아요를 누릅니다.")
    ResponseEntity<Void> likeQuest(
            @Parameter(description = "퀘스트 ID", example = "1") @PathVariable(name = "questId") Long questId,
            @Parameter(description = "로그인 사용자 정보", hidden = true) @AuthenticationPrincipal CustomUserDetails member);

    @Operation(summary = "퀘스트 좋아요 취소 (자기자신)", description = "특정 퀘스트의 좋아요를 취소합니다.")
    ResponseEntity<Void> unlikeQuest(
            @Parameter(description = "퀘스트 ID", example = "1") @PathVariable(name = "questId") Long questId,
            @Parameter(description = "로그인 사용자 정보", hidden = true) @AuthenticationPrincipal CustomUserDetails member);

}
