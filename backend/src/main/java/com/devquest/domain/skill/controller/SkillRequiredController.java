package com.devquest.domain.skill.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@RestController
@RequestMapping("/api/skills")
public class SkillRequiredController {

    @GetMapping("/{skillId}/required/quests")
    public ResponseEntity<Void> getSkillRequiredQuests(
            @PathVariable(name = "skillId") Long skillId) {
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{skillId}/required/skills")
    public ResponseEntity<Void> getSkillRequiredSkills(
            @PathVariable(name = "skillId") Long skillId) {
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{skillId}/required/monsters")
    public ResponseEntity<Void> getSkillRequiredMonsters(
            @PathVariable(name = "skillId") Long skillId) {
        return ResponseEntity.ok().build();
    }

    @PostMapping("/{skillId}/required/quest/{requiredQuestId}")
    public ResponseEntity<Void> createSkillRequiredQuest(
            @PathVariable(name = "skillId") Long skillId,
            @PathVariable(name = "requiredQuestId") Long requiredQuestId) {
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @DeleteMapping("/{skillId}/required/quest/{requiredQuestId}")
    public ResponseEntity<Void> deleteSkillRequiredQuest(
            @PathVariable(name = "skillId") Long skillId,
            @PathVariable(name = "requiredQuestId") Long requiredQuestId) {
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @PostMapping("/{skillId}/required/skill/{requiredSkillId}")
    public ResponseEntity<Void> createSkillRequiredSkill(
            @PathVariable(name = "skillId") Long skillId,
            @PathVariable(name = "requiredSkillId") Long requiredSkillId) {
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @DeleteMapping("/{skillId}/required/skill/{requiredSkillId}")
    public ResponseEntity<Void> deleteSkillRequiredSkill(
            @PathVariable(name = "skillId") Long skillId,
            @PathVariable(name = "requiredSkillId") Long requiredSkillId) {
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @PostMapping("/{skillId}/required/monster/{requiredMonsterId}")
    public ResponseEntity<Void> createSkillRequiredMonster(
            @PathVariable(name = "skillId") Long skillId,
            @PathVariable(name = "requiredMonsterId") Long requiredMonsterId) {
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @DeleteMapping("/{skillId}/monster/{requiredMonsterId}")
    public ResponseEntity<Void> deleteSkillRequiredMonster(
            @PathVariable(name = "skillId") Long skillId,
            @PathVariable(name = "requiredMonsterId") Long requiredMonsterId) {
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

}
