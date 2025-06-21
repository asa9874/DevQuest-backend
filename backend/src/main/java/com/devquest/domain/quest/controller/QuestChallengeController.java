package com.devquest.domain.quest.controller;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.devquest.domain.quest.dto.requestDto.QuestChallengeCreateRequestDto;
import com.devquest.domain.quest.dto.responseDto.QuestChallengeResponseDto;
import com.devquest.domain.quest.service.QuestChallengeService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/quests/challenges")
@RequiredArgsConstructor
public class QuestChallengeController implements QuestChallengeApi {
    private final QuestChallengeService questChallengeService;


    // TODO
    @GetMapping("/member/{memberId}")
    @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
    public ResponseEntity<List<QuestChallengeResponseDto>> getQuestChallengesByMemberId(
            @PathVariable(name = "memberId") Long memberId,
            @RequestParam(required = false) Boolean completed,
            @RequestParam(required = false) Boolean liked,
            @RequestParam(required = false) String title,
            Pageable pageable) {
        return ResponseEntity.ok(null);
    }

    // TODO
    @GetMapping("/quest/{questId}")
    @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
    public ResponseEntity<List<QuestChallengeResponseDto>> getQuestChallengesByQuestId(
            @PathVariable(name = "questId") Long questId,
            @RequestParam(required = false) Boolean completed,
            Pageable pageable) {
        return ResponseEntity.ok(null);
    }

    // TODO
    @GetMapping("/{questChallengeId}")
    @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
    public ResponseEntity<QuestChallengeResponseDto> getQuestChallengeById(
            @PathVariable(name = "questChallengeId") Long questChallengeId) {
        return ResponseEntity.ok(null);
    }

    // TODO
    @PostMapping
    @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
    public ResponseEntity<QuestChallengeResponseDto> createQuestChallenge(
            @RequestBody QuestChallengeCreateRequestDto requestDto) {
        return ResponseEntity.ok(null);
    }

    // TODO
    @PostMapping("/{questChallengeId}/complete")
    @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
    public ResponseEntity<QuestChallengeResponseDto> completeQuestChallenge(
            @PathVariable(name = "questChallengeId") Long questChallengeId) {
        return ResponseEntity.ok(null);
    }

    // TODO
    @PostMapping("/{questChallengeId}/fail")
    @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
    public ResponseEntity<QuestChallengeResponseDto> failQuestChallenge(
            @PathVariable(name = "questChallengeId") Long questChallengeId) {
        return ResponseEntity.ok(null);
    }

}
