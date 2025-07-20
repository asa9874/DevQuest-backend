package com.devquest.domain.monster.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.devquest.domain.monster.dto.responseDto.MonsterChallengeResponseDto;
import com.devquest.domain.monster.dto.responseDto.QuizChallengeResponseDto;
import com.devquest.domain.monster.service.MonsterChallengeService;
import com.devquest.domain.monster.service.QuizChallengeService;
import com.devquest.global.jwt.CustomUserDetails;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/monsters/")
public class MonsterChallengeController implements MonsterChallengeApi {

    private final MonsterChallengeService monsterChallengeService;
    private final QuizChallengeService quizChallengeService;

    // 몬스터 챌린지 전체 조회
    @GetMapping("challenges")
    public ResponseEntity<List<MonsterChallengeResponseDto>> getAllChallenges() {
        List<MonsterChallengeResponseDto> response = monsterChallengeService.getAllChallenges();
        return ResponseEntity.ok(response);
    }

    // 몬스터 챌린지 조회
    @GetMapping("challenges/{challengeId}")
    public ResponseEntity<MonsterChallengeResponseDto> getChallengeForMonster(
            @PathVariable(name = "challengeId") Long challengeId,
            @AuthenticationPrincipal CustomUserDetails member) {
        MonsterChallengeResponseDto response = monsterChallengeService.getChallengeById(challengeId, member.getId());
        return ResponseEntity.ok(response);
    }

    // 회원의 진행 중인 모든 몬스터 챌린지 조회
    @GetMapping("members/{memberId}/challenges")
    public ResponseEntity<List<MonsterChallengeResponseDto>> getOngoingChallengesForMember(
            @PathVariable(name = "memberId") Long memberId) {
        List<MonsterChallengeResponseDto> response = monsterChallengeService.getOngoingChallengesByMemberId(memberId);
        return ResponseEntity.ok(response);
    }

    // 몬스터 챌린지 퀴즈 조회
    @GetMapping("challenges/{challengeId}/quizchallenge")
    public ResponseEntity<List<QuizChallengeResponseDto>> getQuizChallengeByMonsterChallengeId(
            @PathVariable(name = "challengeId") Long challengeId,
            @AuthenticationPrincipal CustomUserDetails member) {
        List<QuizChallengeResponseDto> response = quizChallengeService.getQuizChallengeByMonsterChallengeId(challengeId, member.getId());
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
