package com.devquest.domain.monster.controller;

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

import com.devquest.domain.monster.dto.requestDto.MonsterCreateRequestDto;
import com.devquest.domain.monster.dto.requestDto.MonsterUpdateRequestDto;
import com.devquest.domain.monster.dto.responseDto.MonsterResponseDto;
import com.devquest.domain.monster.service.MonsterService;
import com.devquest.global.jwt.CustomUserDetails;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/monsters/")
public class MonsterController implements MonsterApi {
    private final MonsterService monsterService;

    // 전체조회
    @GetMapping
    public ResponseEntity<List<MonsterResponseDto>> getAllMonsters() {
        List<MonsterResponseDto> responseDtos = monsterService.getAllMonsters();
        return ResponseEntity.ok(responseDtos);
    }

    // 단일조회
    @GetMapping("/{monsterId}")
    public ResponseEntity<MonsterResponseDto> getMonsterById(
            @PathVariable(name = "monsterId") Long monsterId) {
        MonsterResponseDto responseDto = monsterService.getMonsterById(monsterId);
        return ResponseEntity.ok(responseDto);
    }

    // 등록
    @PostMapping
    public ResponseEntity<Void> createMonster(
            @Valid @RequestBody MonsterCreateRequestDto requestDto,
            @AuthenticationPrincipal CustomUserDetails member) {
        monsterService.createMonster(requestDto, member.getId());
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    // 수정
    @PutMapping("/{monsterId}")
    public ResponseEntity<Void> updateMonster(
            @PathVariable(name = "monsterId") Long monsterId,
            @Valid @RequestBody MonsterUpdateRequestDto requestDto,
            @AuthenticationPrincipal CustomUserDetails member) {
        monsterService.updateMonster(requestDto, monsterId, member.getId());
        return ResponseEntity.ok().build();
    }

    // 삭제
    @DeleteMapping("/{monsterId}")
    public ResponseEntity<Void> deleteMonster(
            @PathVariable(name = "monsterId") Long monsterId,
            @AuthenticationPrincipal CustomUserDetails member) {
        monsterService.deleteMonster(monsterId, member.getId());
        return ResponseEntity.noContent().build();
    }

    //TODO:  검색 페이징 추가 
}
