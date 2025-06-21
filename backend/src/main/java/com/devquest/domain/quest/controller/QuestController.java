package com.devquest.domain.quest.controller;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.devquest.domain.quest.dto.requestDto.QuestCreateRequestDto;
import com.devquest.domain.quest.dto.requestDto.QuestUpdateRequestDto;
import com.devquest.domain.quest.dto.responseDto.QuestResponseDto;
import com.devquest.domain.quest.dto.responseDto.QuestWithLikeResponseDto;
import com.devquest.domain.quest.service.QuestService;
import com.devquest.global.jwt.CustomUserDetails;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/quests")
public class QuestController implements QuestApi{
    private final QuestService questService;

    @PostMapping
    @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
    public ResponseEntity<Void> createQuest(
            @RequestBody QuestCreateRequestDto requestDto,
            @AuthenticationPrincipal CustomUserDetails member) {
        questService.createQuest(requestDto, member.getId());
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<List<QuestWithLikeResponseDto>> getAllQuests(){
        List<QuestWithLikeResponseDto> responseDtos = questService.getAllQuests();
        return ResponseEntity.ok(responseDtos);
    }

    @GetMapping("/{questId}")
    public ResponseEntity<QuestWithLikeResponseDto> getQuest(
            @PathVariable(name = "questId") Long questId){
        QuestWithLikeResponseDto questResponseDto = questService.getQuest(questId);
        return ResponseEntity.ok(questResponseDto);
    }
    
    @GetMapping("/search")
    public ResponseEntity<Page<QuestWithLikeResponseDto>> searchQuests(
            @RequestParam(required = false,name = "title") String title,
            @RequestParam(required = false,name = "creatorName") String creatorName,
            Pageable pageable){
        Page<QuestWithLikeResponseDto> responseDtos = questService.search(title, creatorName, pageable);
        return ResponseEntity.ok(responseDtos);
    }

    
    @PutMapping("/{questId}")
    @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
    public ResponseEntity<QuestResponseDto> updateQuest(
            @PathVariable(name = "questId") Long questId,
            @RequestBody QuestUpdateRequestDto requestDto){
        QuestResponseDto responseDto = questService.updateQuest(questId, requestDto);
        return ResponseEntity.ok(responseDto);
    }

    @DeleteMapping("/{questId}")
    @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
    public ResponseEntity<Void> deleteQuest(
            @PathVariable(name = "questId") Long questId){
        questService.deleteQuest(questId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    //좋아요
    @PostMapping("/{questId}/like")
    @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
    public ResponseEntity<Void> likeQuest(
            @PathVariable(name = "questId") Long questId,
            @AuthenticationPrincipal CustomUserDetails member){
        questService.likeQuest(questId, member.getId());
        return ResponseEntity.ok(null);
    }

    @DeleteMapping("/{questId}/like")
    @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
    public ResponseEntity<Void> unlikeQuest(
            @PathVariable(name = "questId") Long questId,
            @AuthenticationPrincipal CustomUserDetails member){
        questService.unlikeQuest(questId, member.getId());
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

}
