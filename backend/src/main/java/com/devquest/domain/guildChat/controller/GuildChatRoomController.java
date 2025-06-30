package com.devquest.domain.guildChat.controller;

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

import com.devquest.domain.guildChat.dto.requestDto.GuildChatRoomCreateRequestDto;
import com.devquest.domain.guildChat.dto.requestDto.GuildChatRoomUpdateRequestDto;
import com.devquest.domain.guildChat.dto.responseDto.GuildChatRoomResponseDto;
import com.devquest.domain.guildChat.service.GuildChatRoomService;
import com.devquest.global.jwt.CustomUserDetails;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/guildchatrooms/")
@RequiredArgsConstructor
public class GuildChatRoomController {
    private final GuildChatRoomService guildChatRoomService;

    @GetMapping
    public ResponseEntity<List<GuildChatRoomResponseDto>> getGuildChatRoom() {
        return null;
    }

    @GetMapping("/{guildChatRoomId}")
    public ResponseEntity<GuildChatRoomResponseDto> getGuildChatRoomById(
            @PathVariable(name = "guildChatRoomId") Long guildChatRoomId) {
        return null;
    }

    @PostMapping
    public ResponseEntity<Void> createGuildChatRoom(
            @Valid @RequestBody GuildChatRoomCreateRequestDto requestDto,
            @AuthenticationPrincipal CustomUserDetails member
            ) {
        guildChatRoomService.createGuildChatRoom(requestDto, member.getId());
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PutMapping("/{guildChatRoomId}")
    public ResponseEntity<Void> updateGuildChatRoom(
            @PathVariable(name = "guildChatRoomId") Long guildChatRoomId,
            @RequestBody GuildChatRoomUpdateRequestDto requestDto,
            @AuthenticationPrincipal CustomUserDetails member) {
        guildChatRoomService.updateGuildChatRoom(guildChatRoomId, requestDto, member.getId());
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @DeleteMapping("/{guildChatRoomId}")
    public ResponseEntity<Void> deleteGuildChatRoom(
            @PathVariable(name = "guildChatRoomId") Long guildChatRoomId,
            @AuthenticationPrincipal CustomUserDetails member) {
        guildChatRoomService.deleteGuildChatRoom(guildChatRoomId, member.getId());
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}