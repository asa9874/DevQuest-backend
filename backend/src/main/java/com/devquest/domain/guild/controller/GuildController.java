package com.devquest.domain.guild.controller;

import java.util.List;

import jakarta.validation.Valid;

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

import com.devquest.domain.guild.dto.requestDto.GuildCreateRequestDto;
import com.devquest.domain.guild.dto.requestDto.GuildUpdateRequestDto;
import com.devquest.domain.guild.dto.responseDto.GuildResponseDto;
import com.devquest.domain.guild.service.GuildService;
import com.devquest.global.jwt.CustomUserDetails;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/guilds")
public class GuildController implements GuildApi {
    private final GuildService guildService;

    @PostMapping
    @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
    public ResponseEntity<Void> createGuild(
            @Valid @RequestBody GuildCreateRequestDto requestDto,
            @AuthenticationPrincipal CustomUserDetails member) {
        guildService.createGuild(requestDto, member.getId());
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<List<GuildResponseDto>> getAllGuilds() {
        List<GuildResponseDto> guilds = guildService.getAllGuilds();
        return ResponseEntity.ok().body(guilds);
    }

    @GetMapping("/search")
    public ResponseEntity<Page<GuildResponseDto>> searchGuilds(
            @RequestParam(required = false, name = "name") String name,
            Pageable pageable) {
        Page<GuildResponseDto> guilds = guildService.searchGuilds(name, pageable);
        return ResponseEntity.ok().body(guilds);
    }

    @GetMapping("/{guildId}")
    public ResponseEntity<GuildResponseDto> getGuildById(
            @PathVariable(name = "guildId") Long guildId) {
        GuildResponseDto guild = guildService.getGuildById(guildId);
        return ResponseEntity.ok().body(guild);
    }

    @PutMapping("/{guildId}")
    @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
    public ResponseEntity<GuildResponseDto> updateGuild(
            @Valid @RequestBody GuildUpdateRequestDto requestDto,
            @PathVariable(name = "guildId") Long guildId) {
        GuildResponseDto responseDto = guildService.updateGuild(guildId, requestDto);
        return ResponseEntity.ok().body(responseDto);
    }

    @DeleteMapping("/{guildId}")
    @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
    public ResponseEntity<Void> deleteGuild(
            @PathVariable(name = "guildId") Long guildId) {
        guildService.deleteGuild(guildId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

}
