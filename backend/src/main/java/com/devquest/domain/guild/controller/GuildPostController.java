package com.devquest.domain.guild.controller;

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

import com.devquest.domain.guild.dto.requestDto.GuildPostCreateRequestDto;
import com.devquest.domain.guild.dto.requestDto.GuildPostUpdateRequestDto;
import com.devquest.domain.guild.dto.responseDto.GuildPostResponseDto;
import com.devquest.domain.guild.service.GuildPostService;
import com.devquest.global.jwt.CustomUserDetails;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/guilds")
@RequiredArgsConstructor
public class GuildPostController implements GuildPostApi {
    private final GuildPostService guildPostService;

    @PostMapping("/posts")
    @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
    public ResponseEntity<Void> createGuildPost(
            @Valid @RequestBody GuildPostCreateRequestDto requestDto,
            @AuthenticationPrincipal CustomUserDetails member) {
        guildPostService.createGuildPost(requestDto, member.getId());
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("/posts/{postId}")
    @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
    public ResponseEntity<GuildPostResponseDto> getGuildPost(
            @PathVariable(name = "postId") Long postId) {
        GuildPostResponseDto responseDto = guildPostService.getGuildPost(postId);
        return ResponseEntity.ok(responseDto);
    }

    //TODO: 나중에 페이징
    @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
    @GetMapping("{guildId}/posts")
    public ResponseEntity<List<GuildPostResponseDto>> getGuildPostsByGuildId(
            @PathVariable(name = "guildId") Long guildId) {
        List<GuildPostResponseDto> responseDtos = guildPostService.getGuildPostsByGuildId(guildId);
        return ResponseEntity.ok(responseDtos);
    }


    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/posts")
    public ResponseEntity<List<GuildPostResponseDto>> getGuildPosts() {
        List<GuildPostResponseDto> responseDtos = guildPostService.getAllGuildPosts();
        return ResponseEntity.ok(responseDtos);
    }

    @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
    @PutMapping("/posts/{postId}")
    public ResponseEntity<Void> updateGuildPost(
            @PathVariable(name = "postId") Long postId,
            @Valid @RequestBody GuildPostUpdateRequestDto requestDto) {
        guildPostService.updateGuildPost(postId, requestDto);
        return ResponseEntity.noContent().build();
    }

    // TODO
    @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
    @DeleteMapping("/posts/{postId}")
    public ResponseEntity<Void> deleteGuildPost(
            @PathVariable(name = "postId") Long postId) {
        guildPostService.deleteGuildPost(postId);
        return ResponseEntity.noContent().build();
    }

}
