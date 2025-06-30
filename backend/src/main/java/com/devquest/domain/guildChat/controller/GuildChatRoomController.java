package com.devquest.domain.guildChat.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.devquest.domain.guildChat.dto.requestDto.GuildChatRoomCreateRequestDto;
import com.devquest.domain.guildChat.dto.requestDto.GuildChatRoomUpdateRequestDto;
import com.devquest.domain.guildChat.dto.responseDto.GuildChatRoomResponseDto;

import io.swagger.v3.oas.annotations.parameters.RequestBody;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/guildchatrooms/")
@RequiredArgsConstructor
public class GuildChatRoomController {

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
            @RequestBody GuildChatRoomCreateRequestDto requestDto) {
        return null;
    }

    @PutMapping("/{guildChatRoomId}")
    public ResponseEntity<GuildChatRoomResponseDto> updateGuildChatRoom(
            @PathVariable(name = "guildChatRoomId") Long guildChatRoomId,
            @RequestBody GuildChatRoomUpdateRequestDto requestDto) {
        return null;
    }

    @DeleteMapping("/{guildChatRoomId}")
    public ResponseEntity<Void> deleteGuildChatRoom(
            @PathVariable(name = "guildChatRoomId") Long guildChatRoomId) {
        return null;
    }
}