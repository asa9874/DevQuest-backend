package com.devquest.domain.quest.controller;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.devquest.domain.quest.dto.responseDto.QuestResponseDto;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/quests/challenges")
@RequiredArgsConstructor
public class QuestChallengeController {

    //TODO
    @GetMapping("/member/{memberId}")
    @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
    public ResponseEntity<List<QuestResponseDto>> getQuestsByMemberId(
            @PathVariable(name = "memberId") Long memberId,
            @RequestParam(required = false) Boolean completed,
            @RequestParam(required = false) Boolean liked,
            @RequestParam(required = false) String title,
            Pageable pageable){
        return ResponseEntity.ok(null);
    }


}
