package com.devquest.domain.skill.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.devquest.domain.skill.dto.requestDto.SkillCreateRequestDto;
import com.devquest.domain.skill.dto.requestDto.SkillUpdateRequestDto;
import com.devquest.domain.skill.dto.responseDto.SkillResponseDto;
import com.devquest.domain.skill.service.SkillService;
import com.devquest.global.jwt.CustomUserDetails;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/skills")
public class SkillController implements SkillApi {
    private final SkillService skillService;

    @GetMapping
    public ResponseEntity<List<SkillResponseDto>> getSkills() {
        List<SkillResponseDto> skills = skillService.getSkills();
        return ResponseEntity.ok(skills);
    }

    @GetMapping("/{skillId}")
    public ResponseEntity<SkillResponseDto> getSkill(
            @PathVariable(name = "skillId") Long skillId) {
        SkillResponseDto skill = skillService.getSkill(skillId);
        return ResponseEntity.ok(skill);
    }

    @PostMapping
    public ResponseEntity<Void> createSkill(
            @RequestBody SkillCreateRequestDto skillCreateRequestDto,
            @AuthenticationPrincipal CustomUserDetails member) {
        skillService.createSkill(skillCreateRequestDto, member.getId());
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PutMapping("/{skillId}")
    public ResponseEntity<Void> updateSkill(
            @PathVariable(name = "skillId") Long skillId,
            @RequestBody SkillUpdateRequestDto skillUpdateRequestDto) {
        skillService.updateSkill(skillId, skillUpdateRequestDto);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{skillId}")
    public ResponseEntity<Void> deleteSkill(
            @PathVariable(name = "skillId") Long skillId) {
        skillService.deleteSkill(skillId);
        return ResponseEntity.noContent().build();
    }

}
