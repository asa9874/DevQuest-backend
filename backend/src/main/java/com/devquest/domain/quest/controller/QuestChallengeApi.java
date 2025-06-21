package com.devquest.domain.quest.controller;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import com.devquest.domain.quest.dto.responseDto.QuestWithLikeResponseDto;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;

public interface QuestChallengeApi {

    
    @Operation(summary = "회원별 퀘스트 목록 조회 (자기자신 or 어드민)", description = "특정 회원이 참여한 퀘스트 목록을 조회합니다.")
    ResponseEntity<List<QuestWithLikeResponseDto>> getQuestsByMemberId(
            @Parameter(description = "회원 ID", example = "1") @PathVariable(name = "memberId") Long memberId,
            @Parameter(description = "완료 여부") @RequestParam(required = false) Boolean completed,
            @Parameter(description = "좋아요 여부") @RequestParam(required = false) Boolean liked,
            @Parameter(description = "퀘스트 제목") @RequestParam(required = false) String title,
            Pageable pageable);
}