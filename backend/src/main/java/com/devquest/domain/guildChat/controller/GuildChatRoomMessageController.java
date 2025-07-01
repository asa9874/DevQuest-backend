package com.devquest.domain.guildChat.controller;

import java.util.List;

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
import org.springframework.web.bind.annotation.RestController;

import com.devquest.domain.guildChat.dto.requestDto.GuildChatRoomMessageCreateRequestDto;
import com.devquest.domain.guildChat.dto.requestDto.GuildChatRoomMessageUpdateRequestDto;
import com.devquest.domain.guildChat.dto.responseDto.GuildChatRoomMessageResponseDto;
import com.devquest.domain.guildChat.service.GuildChatRoomMessageService;
import com.devquest.global.jwt.CustomUserDetails;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/guildchatrooms/")
public class GuildChatRoomMessageController {
    private final GuildChatRoomMessageService guildChatRoomMessageService;

    @GetMapping("/messages")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<List<GuildChatRoomMessageResponseDto>> getAllMessages() {
        List<GuildChatRoomMessageResponseDto> responseDtos = guildChatRoomMessageService.getAllMessages();
        return ResponseEntity.ok(responseDtos);
    }

    @GetMapping("/messages/{messageId}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<GuildChatRoomMessageResponseDto> getMessageById(
            @PathVariable(name = "messageId") Long messageId) {
        GuildChatRoomMessageResponseDto responseDto = guildChatRoomMessageService.getMessageById(messageId);
        return ResponseEntity.ok(responseDto);
    }

    @GetMapping("/{guildChatRoomId}/messages")
    @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
    public ResponseEntity<List<GuildChatRoomMessageResponseDto>> getMessagesByGuildChatRoomId(
            @PathVariable(name = "guildChatRoomId") Long guildChatRoomId,
            @AuthenticationPrincipal CustomUserDetails member) {
        List<GuildChatRoomMessageResponseDto> responseDtos = guildChatRoomMessageService.getMessagesByGuildChatRoomId(
                guildChatRoomId, member.getId());
        return ResponseEntity.ok(responseDtos);
    }

    @PostMapping("/{guildChatRoomId}/messages")
    @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
    public ResponseEntity<Void> createMessage(
            @PathVariable(name = "guildChatRoomId") Long guildChatRoomId,
            @RequestBody GuildChatRoomMessageCreateRequestDto requestDto,
            @AuthenticationPrincipal CustomUserDetails member) {
        guildChatRoomMessageService.createMessage(guildChatRoomId, requestDto, member.getId());
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PutMapping("/messages/{messageId}")
    @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
    public ResponseEntity<Void> updateMessage(
            @PathVariable(name = "messageId") Long messageId,
            @RequestBody GuildChatRoomMessageUpdateRequestDto requestDto,
            @AuthenticationPrincipal CustomUserDetails member) {
        guildChatRoomMessageService.updateMessage(messageId, requestDto, member.getId());
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/messages/{messageId}")
    @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
    public ResponseEntity<Void> deleteMessage(
            @PathVariable(name = "messageId") Long messageId,
            @AuthenticationPrincipal CustomUserDetails member) {
        guildChatRoomMessageService.deleteMessage(messageId, member.getId());
        return ResponseEntity.noContent().build();
    }

}
