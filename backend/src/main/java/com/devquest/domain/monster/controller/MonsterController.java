package com.devquest.domain.monster.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.devquest.domain.monster.dto.responseDto.MonsterResponseDto;

@RestController
@RequestMapping("/api/monsters/")
public class MonsterController {

    // 전체조회
    @GetMapping
    public ResponseEntity<List<MonsterResponseDto>> getAllMonsters() {
        return null;
    }
}
