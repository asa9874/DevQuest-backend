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
import org.springframework.web.bind.annotation.RestController;

import com.devquest.domain.guild.dto.requestDto.GuildPostCreateRequestDto;
import com.devquest.domain.guild.dto.requestDto.GuildPostUpdateRequestDto;
import com.devquest.domain.guild.dto.responseDto.GuildPostResponseDto;
import com.devquest.domain.guild.dto.responseDto.GuildResponseDto;
import com.devquest.domain.guild.service.GuildPostService;
import com.devquest.global.jwt.CustomUserDetails;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/guilds")
@RequiredArgsConstructor
public class GuildPostController {
    private final GuildPostService guildPostService;

    @PostMapping("/posts")
    public ResponseEntity<Void> createGuildPost(
            @Valid @RequestBody GuildPostCreateRequestDto requestDto,
            @AuthenticationPrincipal CustomUserDetails member) {
        guildPostService.createGuildPost(requestDto, member.getId());
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    // TODO
    @GetMapping("/posts/{postId}")
    public ResponseEntity<GuildPostResponseDto> getGuildPost(
            @PathVariable(name = "postId") Long postId) {
        return null;
    }

    // TODO
    @GetMapping("/posts")
    public ResponseEntity<List<GuildResponseDto>> getGuildPosts() {
        return ResponseEntity.ok().build();
    }

    // TODO
    @PutMapping("/posts/{postId}")
    public ResponseEntity<Void> updateGuildPost(
            @PathVariable(name = "postId") Long postId,
            @Valid @RequestBody GuildPostUpdateRequestDto requestDto) {
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    // TODO
    @DeleteMapping("/posts/{postId}")
    public ResponseEntity<Void> deleteGuildPost(
            @PathVariable(name = "postId") Long postId,
            @AuthenticationPrincipal CustomUserDetails member) {
        return ResponseEntity.noContent().build();
    }

}
