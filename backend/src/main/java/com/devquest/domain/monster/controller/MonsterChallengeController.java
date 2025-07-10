package com.devquest.domain.monster.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.devquest.domain.monster.dto.responseDto.MonsterChallengeResponseDto;
import com.devquest.domain.monster.service.MonsterChallengeService;
import com.devquest.global.jwt.CustomUserDetails;

import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.GetMapping;


@RestController
@RequestMapping("/api/monsters/")
@RequiredArgsConstructor
public class MonsterChallengeController {

    private final MonsterChallengeService monsterChallengeService;

    // 몬스터 챌린지 조회
    @GetMapping("challenges/{challengeId}")
    public ResponseEntity<MonsterChallengeResponseDto> getChallengeForMonster(
            @PathVariable(name = "challengeId") Long challengeId,
            @AuthenticationPrincipal CustomUserDetails member) {
        MonsterChallengeResponseDto response = monsterChallengeService.getChallengeById(challengeId, member.getId());
        return ResponseEntity.ok(response);
    }
    

    // 몬스터 챌린지 생성
    @PostMapping("{monsterId}/challenges")
    public ResponseEntity<Void> createChallengeForMonster(
            @PathVariable(name = "monsterId") Long monsterId,
            @AuthenticationPrincipal CustomUserDetails member) {
        monsterChallengeService.createChallengeForMonster(monsterId, member.getId());
        return ResponseEntity.ok().build();
    }

    // 몬스터 챌린지 완료
    @PostMapping("challenges/{challengeId}/complete")
    public ResponseEntity<Void> completeChallengeForMonster(
            @PathVariable(name = "challengeId") Long challengeId,
            @AuthenticationPrincipal CustomUserDetails member) {
        monsterChallengeService.completeChallenge(challengeId);
        return ResponseEntity.ok().build();
    }


}
