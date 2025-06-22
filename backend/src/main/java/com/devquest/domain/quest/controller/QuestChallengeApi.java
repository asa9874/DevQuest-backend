package com.devquest.domain.quest.controller;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import com.devquest.domain.quest.dto.requestDto.QuestChallengeCreateRequestDto;
import com.devquest.domain.quest.dto.responseDto.QuestChallengeResponseDto;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;

public interface QuestChallengeApi {
    @Operation(summary = "회원별 퀘스트 챌린지 목록 조회 (자기자신/어드민)", description = "특정 회원이 참여한 퀘스트 챌린지 목록을 조회합니다.")
    ResponseEntity<List<QuestChallengeResponseDto>> getQuestChallengesByMemberId(
            @Parameter(description = "회원 ID", example = "1") @PathVariable(name = "memberId") Long memberId,
            @Parameter(description = "완료 여부") @RequestParam(required = false) String status,
            @Parameter(description = "퀘스트 제목") @RequestParam(required = false) String title,
            Pageable pageable);

    @Operation(summary = "퀘스트별 챌린지 목록 조회 (퀘스트주인/어드민)", description = "특정 퀘스트에 대한 챌린지 목록을 조회합니다.")
    ResponseEntity<List<QuestChallengeResponseDto>> getQuestChallengesByQuestId(
            @Parameter(description = "퀘스트 ID", example = "1") @PathVariable(name = "questId") Long questId,
            @Parameter(description = "완료 여부") @RequestParam(required = false) String status,
            Pageable pageable);

    @Operation(summary = "퀘스트 챌린지 조회 (자기자신/어드민)", description = "특정 퀘스트 챌린지의 상세 정보를 조회합니다.")
    ResponseEntity<QuestChallengeResponseDto> getQuestChallengeById(
            @Parameter(description = "퀘스트 챌린지 ID", example = "1") @PathVariable(name = "questChallengeId") Long questChallengeId);

    @Operation(summary = "퀘스트 챌린지 생성 (유저)", description = "새로운 퀘스트 챌린지를 생성합니다.")
    ResponseEntity<Void> createQuestChallenge(
            @Parameter(description = "퀘스트 챌린지 생성 요청 DTO") @RequestBody QuestChallengeCreateRequestDto requestDto);

    @Operation(summary = "퀘스트 챌린지 완료 처리 (자기자신)", description = "특정 퀘스트 챌린지를 완료 처리합니다.")
    ResponseEntity<Void> completeQuestChallenge(
            @Parameter(description = "퀘스트 챌린지 ID", example = "1") @PathVariable(name = "questChallengeId") Long questChallengeId);

    @Operation(summary = "퀘스트 챌린지 실패 처리 (자기자신)", description = "특정 퀘스트 챌린지를 실패 처리합니다.")
    ResponseEntity<Void> failQuestChallenge(
            @Parameter(description = "퀘스트 챌린지 ID", example = "1") @PathVariable(name = "questChallengeId") Long questChallengeId);
}