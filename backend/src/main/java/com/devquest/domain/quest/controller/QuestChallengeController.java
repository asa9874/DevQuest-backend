package com.devquest.domain.quest.controller;

import java.util.List;

import jakarta.validation.Valid;

import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.devquest.domain.quest.dto.requestDto.QuestChallengeCreateRequestDto;
import com.devquest.domain.quest.dto.responseDto.QuestChallengeResponseDto;
import com.devquest.domain.quest.model.QuestStatus;
import com.devquest.domain.quest.service.QuestChallengeService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/quests/challenges")
public class QuestChallengeController implements QuestChallengeApi {
    private final QuestChallengeService questChallengeService;

    @PostMapping
    @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
    public ResponseEntity<Void> createQuestChallenge(
            @Valid @RequestBody QuestChallengeCreateRequestDto requestDto) {
        questChallengeService.createQuestChallenge(requestDto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("/member/{memberId}")
    @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
    public ResponseEntity<List<QuestChallengeResponseDto>> getQuestChallengesByMemberId(
            @PathVariable(name = "memberId") Long memberId,
            @RequestParam(required = false,name = "status") String status,
            @RequestParam(required = false, name = "title") String title,
            Pageable pageable) {
        QuestStatus questStatus = (status == null || status.isBlank()) ? null : QuestStatus.valueOf(status);
        List<QuestChallengeResponseDto> questChallenges = questChallengeService.getQuestChallengesByMemberId(
                memberId, questStatus, title, pageable);
        return ResponseEntity.ok(questChallenges);
    }

    @GetMapping("/quest/{questId}")
    @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
    public ResponseEntity<List<QuestChallengeResponseDto>> getQuestChallengesByQuestId(
            @PathVariable(name = "questId") Long questId,
            @RequestParam(required = false, name="status") String status,
            Pageable pageable) {
        QuestStatus questStatus = (status == null || status.isBlank()) ? null : QuestStatus.valueOf(status);
        List<QuestChallengeResponseDto> questChallenges = questChallengeService.getQuestChallengesByQuestId(
                questId, questStatus, pageable);
        return ResponseEntity.ok(questChallenges);
    }

    @GetMapping("/{questChallengeId}")
    @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
    public ResponseEntity<QuestChallengeResponseDto> getQuestChallengeById(
            @PathVariable(name = "questChallengeId") Long questChallengeId) {
        QuestChallengeResponseDto questChallengeResponseDto = questChallengeService.getQuestChallenge(questChallengeId);
        return ResponseEntity.ok(questChallengeResponseDto);
    }

    @PutMapping("/{questChallengeId}/complete")
    @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
    public ResponseEntity<Void> completeQuestChallenge(
            @PathVariable(name = "questChallengeId") Long questChallengeId) {
        questChallengeService.completeQuestChallenge(questChallengeId);
        return ResponseEntity.ok(null);
    }

    @PutMapping("/{questChallengeId}/fail")
    @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
    public ResponseEntity<Void> failQuestChallenge(
            @PathVariable(name = "questChallengeId") Long questChallengeId) {
        questChallengeService.failQuestChallenge(questChallengeId);
        return ResponseEntity.ok(null);
    }

}
