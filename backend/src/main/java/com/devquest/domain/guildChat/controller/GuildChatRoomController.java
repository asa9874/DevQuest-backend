package com.devquest.domain.guildChat.controller;

import java.util.List;

import jakarta.validation.Valid;

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

import com.devquest.domain.guildChat.dto.requestDto.GuildChatRoomCreateRequestDto;
import com.devquest.domain.guildChat.dto.requestDto.GuildChatRoomUpdateRequestDto;
import com.devquest.domain.guildChat.dto.responseDto.GuildChatRoomResponseDto;
import com.devquest.domain.guildChat.service.GuildChatRoomService;
import com.devquest.global.jwt.CustomUserDetails;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/guildchatrooms/")
public class GuildChatRoomController implements GuildChatRoomApi {
    private final GuildChatRoomService guildChatRoomService;

    @GetMapping
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<List<GuildChatRoomResponseDto>> getGuildChatRoom() {
        List<GuildChatRoomResponseDto> guildChatRooms = guildChatRoomService.getAllGuildChatRooms();
        return ResponseEntity.ok(guildChatRooms);
    }

    @GetMapping("/{guildChatRoomId}")
    @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
    public ResponseEntity<GuildChatRoomResponseDto> getGuildChatRoomById(
            @PathVariable(name = "guildChatRoomId") Long guildChatRoomId,
            @AuthenticationPrincipal CustomUserDetails member) {
        GuildChatRoomResponseDto guildChatRoom = guildChatRoomService.getGuildChatRoomById(guildChatRoomId,
                member.getId());
        return ResponseEntity.ok(guildChatRoom);
    }

    @GetMapping("/guild/{guildId}")
    @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
    public ResponseEntity<List<GuildChatRoomResponseDto>> getGuildChatRoomsByGuildId(
            @PathVariable(name = "guildId") Long guildId,
            @AuthenticationPrincipal CustomUserDetails member) {
        List<GuildChatRoomResponseDto> guildChatRooms = guildChatRoomService.getGuildChatRoomsByGuildId(guildId,
                member.getId());
        return ResponseEntity.ok(guildChatRooms);
    }

    @PostMapping
    @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
    public ResponseEntity<Void> createGuildChatRoom(
            @Valid @RequestBody GuildChatRoomCreateRequestDto requestDto,
            @AuthenticationPrincipal CustomUserDetails member) {
        guildChatRoomService.createGuildChatRoom(requestDto, member.getId());
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PutMapping("/{guildChatRoomId}")
    @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
    public ResponseEntity<Void> updateGuildChatRoom(
            @PathVariable(name = "guildChatRoomId") Long guildChatRoomId,
            @RequestBody GuildChatRoomUpdateRequestDto requestDto,
            @AuthenticationPrincipal CustomUserDetails member) {
        guildChatRoomService.updateGuildChatRoom(guildChatRoomId, requestDto, member.getId());
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @DeleteMapping("/{guildChatRoomId}")
    @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
    public ResponseEntity<Void> deleteGuildChatRoom(
            @PathVariable(name = "guildChatRoomId") Long guildChatRoomId,
            @AuthenticationPrincipal CustomUserDetails member) {
        guildChatRoomService.deleteGuildChatRoom(guildChatRoomId, member.getId());
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
