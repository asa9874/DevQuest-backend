package com.devquest.domain.monster.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.devquest.global.jwt.CustomUserDetails;

@RestController
@RequestMapping("/api/monsters/")
public class MonsterChallengeController {

    // 몬스터 챌린지 생성
    @PostMapping("{monsterId}/challenges")
    public ResponseEntity<Void> createChallengeForMonster(
            @PathVariable(name = "monsterId") Long monsterId,
            @AuthenticationPrincipal CustomUserDetails member) {
        return null;
    }

    // 몬스터 챌린지 완료
    @PostMapping("{monsterId}/challenges/{challengeId}/complete")
    public ResponseEntity<Void> completeChallengeForMonster(
            @PathVariable(name = "monsterId") Long monsterId,
            @PathVariable(name = "challengeId") Long challengeId,
            @AuthenticationPrincipal CustomUserDetails member) {
        return null;
    }
}
