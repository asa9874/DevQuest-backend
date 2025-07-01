package com.devquest.domain.guildChat.controller;

import java.util.List;

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
import org.springframework.web.bind.annotation.RestController;

import com.devquest.domain.guildChat.dto.requestDto.GuildChatRoomMessageCreateRequestDto;
import com.devquest.domain.guildChat.dto.requestDto.GuildChatRoomMessageUpdateRequestDto;
import com.devquest.domain.guildChat.dto.responseDto.GuildChatRoomMessageResponseDto;
import com.devquest.global.jwt.CustomUserDetails;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/guildchatrooms/")
public class GuildChatRoomMessageController {

    @GetMapping("/messages")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<List<GuildChatRoomMessageResponseDto>> getAllMessages() {
        return null;
    }

    @GetMapping("/messages/{messageId}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<GuildChatRoomMessageResponseDto> getMessageById(
            @PathVariable(name = "messageId") Long messageId) {
        return null;
    }

    @GetMapping("/{guildChatRoomId}/messages")
    @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
    public ResponseEntity<List<GuildChatRoomMessageResponseDto>> getMessagesByGuildChatRoomId(
            @PathVariable(name = "guildChatRoomId") Long guildChatRoomId,
            @AuthenticationPrincipal CustomUserDetails member) {
        return null;
    }

    @PostMapping("/{guildChatRoomId}/messages")
    @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
    public ResponseEntity<GuildChatRoomMessageResponseDto> createMessage(
            @PathVariable(name = "guildChatRoomId") Long guildChatRoomId,
            @RequestBody GuildChatRoomMessageCreateRequestDto requestDto,
            @AuthenticationPrincipal CustomUserDetails member) {
        return null;
    }

    @PutMapping("/messages/{messageId}")
    @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
    public ResponseEntity<GuildChatRoomMessageResponseDto> updateMessage(
            @PathVariable(name = "messageId") Long messageId,
            @RequestBody GuildChatRoomMessageUpdateRequestDto requestDto) {
        return null;
    }

    @DeleteMapping("/messages/{messageId}")
    @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
    public ResponseEntity<Void> deleteMessage(
            @PathVariable(name = "messageId") Long messageId) {
        return null;
    }

}
