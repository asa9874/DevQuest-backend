package com.devquest.domain.guildChat.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.devquest.domain.guildChat.dto.requestDto.GuildChatRoomMessageCreateRequestDto;
import com.devquest.domain.guildChat.dto.requestDto.GuildChatRoomMessageUpdateRequestDto;
import com.devquest.domain.guildChat.dto.responseDto.GuildChatRoomMessageResponseDto;
import com.devquest.global.jwt.CustomUserDetails;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "길드 채팅방 메시지 API", description = "길드 채팅방 메시지 관련 API")
public interface GuildChatRoomMessageApi {

    @Operation(summary = "모든 메시지 조회 (어드민)", description = "모든 길드 채팅방 메시지를 조회합니다.")
    @GetMapping("/messages")
    ResponseEntity<List<GuildChatRoomMessageResponseDto>> getAllMessages();

    @Operation(summary = "메시지 상세 조회 (어드민)", description = "특정 메시지의 상세 정보를 조회합니다.")
    @GetMapping("/messages/{messageId}")
    ResponseEntity<GuildChatRoomMessageResponseDto> getMessageById(
            @Parameter(description = "메시지 ID", example = "1") @PathVariable(name = "messageId") Long messageId);

    @Operation(summary = "채팅방별 메시지 목록 조회 (길드 멤버)", description = "특정 채팅방의 모든 메시지를 조회합니다.")
    @GetMapping("/{guildChatRoomId}/messages")
    ResponseEntity<List<GuildChatRoomMessageResponseDto>> getMessagesByGuildChatRoomId(
            @Parameter(description = "채팅방 ID", example = "1") @PathVariable(name = "guildChatRoomId") Long guildChatRoomId,
            @Parameter(description = "로그인 사용자 정보", hidden = true) @AuthenticationPrincipal CustomUserDetails member);

    @Operation(summary = "메시지 작성 (길드 멤버)", description = "채팅방에 새로운 메시지를 작성합니다.")
    @PostMapping("/{guildChatRoomId}/messages")
    ResponseEntity<Void> createMessage(
            @Parameter(description = "채팅방 ID", example = "1") @PathVariable(name = "guildChatRoomId") Long guildChatRoomId,
            @Parameter(description = "메시지 생성 요청 DTO") @RequestBody GuildChatRoomMessageCreateRequestDto requestDto,
            @Parameter(description = "로그인 사용자 정보", hidden = true) @AuthenticationPrincipal CustomUserDetails member);

    @Operation(summary = "메시지 수정 (메시지 작성자/어드민)", description = "작성한 메시지를 수정합니다.")
    @PutMapping("/messages/{messageId}")
    ResponseEntity<Void> updateMessage(
            @Parameter(description = "메시지 ID", example = "1") @PathVariable(name = "messageId") Long messageId,
            @Parameter(description = "메시지 수정 요청 DTO") @RequestBody GuildChatRoomMessageUpdateRequestDto requestDto,
            @Parameter(description = "로그인 사용자 정보", hidden = true) @AuthenticationPrincipal CustomUserDetails member);

    @Operation(summary = "메시지 삭제 (메시지 작성자/어드민)", description = "작성한 메시지를 삭제합니다.")
    @DeleteMapping("/messages/{messageId}")
    ResponseEntity<Void> deleteMessage(
            @Parameter(description = "메시지 ID", example = "1") @PathVariable(name = "messageId") Long messageId,
            @Parameter(description = "로그인 사용자 정보", hidden = true) @AuthenticationPrincipal CustomUserDetails member);
}
