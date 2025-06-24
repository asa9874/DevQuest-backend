package com.devquest.domain.guild.controller;

import java.util.List;

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

import com.devquest.domain.guild.dto.requestDto.GuildPostCommentCreateRequestDto;
import com.devquest.domain.guild.dto.responseDto.GuildPostCommentResponseDto;
import com.devquest.domain.guild.service.GuildPostCommentService;
import com.devquest.global.jwt.CustomUserDetails;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/guilds")
@RequiredArgsConstructor
public class GuildPostCommentController {
    private final GuildPostCommentService guildPostCommentService;

    // TODO
    @PostMapping("/comments")
    public ResponseEntity<GuildPostCommentResponseDto> createGuildPostComment(
            @PathVariable(name = "postId") Long postId,
            @RequestBody GuildPostCommentCreateRequestDto requestDto,
            @AuthenticationPrincipal CustomUserDetails member) {
        return null;
    }

    // TODO
    @GetMapping("/post/{postId}/comments")
    public ResponseEntity<List<GuildPostCommentResponseDto>> getGuildPostCommentsByPostId(
            @PathVariable(name = "postId") Long postId) {
        return null;
    }

    // TODO
    @GetMapping("/comments/{commentId}")
    public ResponseEntity<GuildPostCommentResponseDto> getGuildPostCommentById(
            @PathVariable(name = "commentId") Long commentId) {
        return null;
    }

    // TODO
    @GetMapping("/comments")
    public ResponseEntity<List<GuildPostCommentResponseDto>> getAllGuildPostComments() {
        return null;
    }

    // TODO
    @PutMapping("/comments/{commentId}")
    public ResponseEntity<Void> updateGuildPostComment(
            @PathVariable(name = "commentId") Long commentId,
            @RequestBody GuildPostCommentResponseDto requestDto) {
        return null;
    }

    // TODO
    @DeleteMapping("/comments/{commentId}")
    public ResponseEntity<Void> deleteGuildPostComment(
            @PathVariable(name = "commentId") Long commentId,
            @AuthenticationPrincipal CustomUserDetails member) {
        return null;
    }

}