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

import com.devquest.domain.guild.dto.requestDto.GuildPostCommentCreateRequestDto;
import com.devquest.domain.guild.dto.requestDto.GuildPostCommentUpdateRequestDto;
import com.devquest.domain.guild.dto.responseDto.GuildPostCommentResponseDto;
import com.devquest.domain.guild.service.GuildPostCommentService;
import com.devquest.global.jwt.CustomUserDetails;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/guilds")
public class GuildPostCommentController implements GuildPostCommentApi {
    private final GuildPostCommentService guildPostCommentService;

    @PostMapping("/comments")
    @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
    public ResponseEntity<Void> createGuildPostComment(
            @RequestBody GuildPostCommentCreateRequestDto requestDto,
            @AuthenticationPrincipal CustomUserDetails member) {
        guildPostCommentService.createGuildPostComment(requestDto, member.getId());
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("/post/{postId}/comments")
    @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
    public ResponseEntity<List<GuildPostCommentResponseDto>> getGuildPostCommentsByPostId(
            @PathVariable(name = "postId") Long postId,
            @AuthenticationPrincipal CustomUserDetails member) {
        List<GuildPostCommentResponseDto> responseDtos = guildPostCommentService.getGuildPostCommentsByPostId(postId,
                member.getId());
        return ResponseEntity.ok(responseDtos);
    }

    @GetMapping("/comments/{commentId}")
    @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
    public ResponseEntity<GuildPostCommentResponseDto> getGuildPostCommentById(
            @PathVariable(name = "commentId") Long commentId,
            @AuthenticationPrincipal CustomUserDetails member) {
        GuildPostCommentResponseDto responseDto = guildPostCommentService.getGuildPostCommentById(commentId,
                member.getId());
        return ResponseEntity.ok(responseDto);
    }

    @GetMapping("/comments")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<List<GuildPostCommentResponseDto>> getAllGuildPostComments() {
        List<GuildPostCommentResponseDto> responseDtos = guildPostCommentService.getAllGuildPostComments();
        return ResponseEntity.ok(responseDtos);
    }

    @PutMapping("/comments/{commentId}")
    @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
    public ResponseEntity<Void> updateGuildPostComment(
            @PathVariable(name = "commentId") Long commentId,
            @RequestBody GuildPostCommentUpdateRequestDto requestDto) {
        guildPostCommentService.updateGuildPostComment(commentId, requestDto);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @DeleteMapping("/comments/{commentId}")
    @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
    public ResponseEntity<Void> deleteGuildPostComment(
            @PathVariable(name = "commentId") Long commentId,
            @AuthenticationPrincipal CustomUserDetails member) {
        guildPostCommentService.deleteGuildPostComment(commentId, member.getId());
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

}
