package com.devquest.domain.skill.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.devquest.domain.skill.dto.responseDto.MemberSkillResponseDto;
import com.devquest.domain.skill.service.MemberSkillService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/members")
public class MemberSkillController {
    private final MemberSkillService memberSkillService;

    @GetMapping("/{memberId}/skills/{skillId}")
    public ResponseEntity<MemberSkillResponseDto> getMemberSkill(
            @PathVariable(name = "memberId") Long memberId,
            @PathVariable(name = "skillId") Long skillId) {
        MemberSkillResponseDto responseDto = memberSkillService.getMemberSkill(memberId, skillId);
        return ResponseEntity.ok(responseDto);
    }

    @GetMapping("/{memberId}/skills")
    public ResponseEntity<List<MemberSkillResponseDto>> getMemberSkills(
            @PathVariable(name = "memberId") Long memberId) {
        List<MemberSkillResponseDto> responseDtos = memberSkillService.getMemberSkills(memberId);
        return ResponseEntity.ok(responseDtos);
    }

    @PostMapping("/{memberId}/skills/{skillId}")
    public ResponseEntity<Void> createMemberSkill(
            @PathVariable(name = "memberId") Long memberId,
            @PathVariable(name = "skillId") Long skillId) {
        memberSkillService.createMemberSkill(memberId, skillId);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @DeleteMapping("/{memberId}/skills/{skillId}")
    public ResponseEntity<Void> deleteMemberSkill(
            @PathVariable(name = "memberId") Long memberId,
            @PathVariable(name = "skillId") Long skillId) {
        memberSkillService.deleteMemberSkill(memberId, skillId);
        return ResponseEntity.noContent().build();
    }
}
