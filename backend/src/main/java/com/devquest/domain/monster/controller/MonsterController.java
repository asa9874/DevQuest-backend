package com.devquest.domain.monster.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.devquest.domain.monster.dto.requestDto.MonsterCreateRequestDto;
import com.devquest.domain.monster.dto.requestDto.MonsterUpdateRequestDto;
import com.devquest.domain.monster.dto.responseDto.MonsterResponseDto;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/monsters/")
public class MonsterController {

    // 전체조회
    @GetMapping
    public ResponseEntity<List<MonsterResponseDto>> getAllMonsters() {
        return null;
    }

    // 단일조회
    @GetMapping("/{monsterId}")
    public ResponseEntity<MonsterResponseDto> getMonsterById(
            @PathVariable(name = "monsterId") Long monsterId) {
        return null;
    }

    // 등록
    @PostMapping
    public ResponseEntity<Void> createMonster(
            @Valid @RequestBody MonsterCreateRequestDto requestDto) {
        return null;
    }

    // 수정
    @PutMapping("/{monsterId}")
    public ResponseEntity<Void> updateMonster(
            @PathVariable(name = "monsterId") Long monsterId,
            @Valid @RequestBody MonsterUpdateRequestDto requestDto) {
        return null;
    }

    // 삭제
    @DeleteMapping("/{monsterId}")
    public ResponseEntity<Void> deleteMonster(
            @PathVariable(name = "monsterId") Long monsterId) {
        return null;
    }
}
