package com.devquest.domain.guild.controller;

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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.devquest.domain.guild.dto.requestDto.GuildCreateRequestDto;
import com.devquest.domain.guild.dto.responseDto.GuildResponseDto;
import com.devquest.domain.guild.service.GuildService;
import com.devquest.global.jwt.CustomUserDetails;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/guilds")
@RequiredArgsConstructor
public class GuildController {
    private final GuildService guildService;

    @PostMapping
    public ResponseEntity<Void> createGuild(
            @RequestBody GuildCreateRequestDto requestDto,
            @AuthenticationPrincipal CustomUserDetails member) {
        guildService.createGuild(requestDto, member.getId());
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    // TODO
    @GetMapping
    public ResponseEntity<List<GuildResponseDto>> getAllGuilds() {
        return ResponseEntity.ok().body(null);
    }

    // TODO
    @GetMapping("/search")
    public ResponseEntity<List<GuildResponseDto>> searchGuilds(
            @RequestParam(required = false, name = "name") String name) {
        return ResponseEntity.ok().body(null);
    }

    // TODO
    @GetMapping("/{guildId}")
    public ResponseEntity<GuildResponseDto> getGuildById(
            @PathVariable(name = "guildId") Long guildId) {
        return ResponseEntity.ok().body(null);
    }

    // TODO
    @PutMapping("/{guildId}")
    public ResponseEntity<GuildResponseDto> updateGuild(
            @RequestBody GuildCreateRequestDto requestDto,
            @PathVariable(name = "guildId") Long guildId) {
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    // TODO
    @DeleteMapping("/{guildId}")
    public ResponseEntity<Void> deleteGuild(
            @PathVariable(name = "guildId") Long guildId) {
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

}
