package com.devquest.domain.guildChat.controller;

import java.util.List;

import jakarta.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.devquest.domain.guildChat.dto.requestDto.GuildChatRoomCreateRequestDto;
import com.devquest.domain.guildChat.dto.requestDto.GuildChatRoomUpdateRequestDto;
import com.devquest.domain.guildChat.dto.responseDto.GuildChatRoomResponseDto;
import com.devquest.global.jwt.CustomUserDetails;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "길드 채팅방 API", description = "길드 채팅방 관련 API")
public interface GuildChatRoomApi {

    @Operation(summary = "모든 채팅방 조회 (어드민)", description = "모든 길드 채팅방을 조회합니다.")
    @GetMapping
    ResponseEntity<List<GuildChatRoomResponseDto>> getGuildChatRoom();

    @Operation(summary = "채팅방 상세 조회 (길드 멤버)", description = "특정 채팅방의 상세 정보를 조회합니다.")
    @GetMapping("/{guildChatRoomId}")
    ResponseEntity<GuildChatRoomResponseDto> getGuildChatRoomById(
            @Parameter(description = "채팅방 ID", example = "1") @PathVariable(name = "guildChatRoomId") Long guildChatRoomId,
            @Parameter(description = "로그인 사용자 정보", hidden = true) @AuthenticationPrincipal CustomUserDetails member);

    @Operation(summary = "길드별 채팅방 목록 조회 (길드 멤버)", description = "특정 길드의 모든 채팅방을 조회합니다.")
    @GetMapping("/guild/{guildId}")
    ResponseEntity<List<GuildChatRoomResponseDto>> getGuildChatRoomsByGuildId(
            @Parameter(description = "길드 ID", example = "1") @PathVariable(name = "guildId") Long guildId,
            @Parameter(description = "로그인 사용자 정보", hidden = true) @AuthenticationPrincipal CustomUserDetails member);

    @Operation(summary = "채팅방 생성 (길드 어드민)", description = "새로운 길드 채팅방을 생성합니다.")
    @PostMapping
    ResponseEntity<Void> createGuildChatRoom(
            @Parameter(description = "채팅방 생성 요청 DTO") @Valid @RequestBody GuildChatRoomCreateRequestDto requestDto,
            @Parameter(description = "로그인 사용자 정보", hidden = true) @AuthenticationPrincipal CustomUserDetails member);

    @Operation(summary = "채팅방 수정 (길드 어드민)", description = "길드 채팅방 정보를 수정합니다.")
    @PutMapping("/{guildChatRoomId}")
    ResponseEntity<Void> updateGuildChatRoom(
            @Parameter(description = "채팅방 ID", example = "1") @PathVariable(name = "guildChatRoomId") Long guildChatRoomId,
            @Parameter(description = "채팅방 수정 요청 DTO") @RequestBody GuildChatRoomUpdateRequestDto requestDto,
            @Parameter(description = "로그인 사용자 정보", hidden = true) @AuthenticationPrincipal CustomUserDetails member);

    @Operation(summary = "채팅방 삭제 (길드 어드민)", description = "길드 채팅방을 삭제합니다.")
    @DeleteMapping("/{guildChatRoomId}")
    ResponseEntity<Void> deleteGuildChatRoom(
            @Parameter(description = "채팅방 ID", example = "1") @PathVariable(name = "guildChatRoomId") Long guildChatRoomId,
            @Parameter(description = "로그인 사용자 정보", hidden = true) @AuthenticationPrincipal CustomUserDetails member);
}
