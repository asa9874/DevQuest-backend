package com.devquest.domain.skill.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/members")
public class MemberSkillController {

    @GetMapping("/{memberId}/skills/{skillId}")
    public ResponseEntity<Void> getMemberSkill(
            @PathVariable(name = "memberId") Long memberId,
            @PathVariable(name = "skillId") Long skillId) {
        return null;
    }

    @GetMapping("/{memberId}/skills")
    public ResponseEntity<List<Void>> getMemberSkills(
            @PathVariable(name = "memberId") Long memberId,
            @PathVariable(name = "skillId") Long skillId) {
        return null;
    }

    @PostMapping("/{memberId}/skills/{skillId}")
    public ResponseEntity<Void> createMemberSkill(
            @PathVariable(name = "memberId") Long memberId,
            @PathVariable(name = "skillId") Long skillId) {
        return null;
    }

    @DeleteMapping("/{memberId}/skills/{skillId}")
    public ResponseEntity<Void> deleteMemberSkill(
            @PathVariable(name = "memberId") Long memberId,
            @PathVariable(name = "skillId") Long skillId) {
        return null;
    }
}
