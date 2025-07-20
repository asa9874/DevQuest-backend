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

import com.devquest.domain.guild.dto.requestDto.GuildPostCommentCreateRequestDto;
import com.devquest.domain.guild.dto.requestDto.GuildPostCommentUpdateRequestDto;
import com.devquest.domain.guild.dto.responseDto.GuildPostCommentResponseDto;
import com.devquest.global.jwt.CustomUserDetails;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "길드 게시글 댓글 API", description = "길드 게시글 댓글 관련 API")
public interface GuildPostCommentApi {

    @Operation(summary = "길드 게시글 댓글 생성 (유저/어드민)", description = "길드 게시글에 댓글을 작성합니다.")
    @PostMapping("/comments")
    ResponseEntity<Void> createGuildPostComment(
            @Parameter(description = "댓글 생성 요청 DTO") @RequestBody GuildPostCommentCreateRequestDto requestDto,
            @Parameter(description = "로그인 사용자 정보", hidden = true) @AuthenticationPrincipal CustomUserDetails member);

    @Operation(summary = "게시글별 댓글 목록 조회 (유저/어드민)", description = "특정 길드 게시글의 모든 댓글을 조회합니다.")
    @GetMapping("/post/{postId}/comments")
    ResponseEntity<List<GuildPostCommentResponseDto>> getGuildPostCommentsByPostId(
            @Parameter(description = "게시글 ID", example = "1") @PathVariable(name = "postId") Long postId,
            @Parameter(description = "로그인 사용자 정보", hidden = true) @AuthenticationPrincipal CustomUserDetails member);

    @Operation(summary = "댓글 상세 조회 (유저/어드민)", description = "특정 댓글의 상세 정보를 조회합니다.")
    @GetMapping("/comments/{commentId}")
    ResponseEntity<GuildPostCommentResponseDto> getGuildPostCommentById(
            @Parameter(description = "댓글 ID", example = "1") @PathVariable(name = "commentId") Long commentId,
            @Parameter(description = "로그인 사용자 정보", hidden = true) @AuthenticationPrincipal CustomUserDetails member);

    @Operation(summary = "모든 댓글 조회 (어드민)", description = "모든 길드 게시글 댓글을 조회합니다.")
    @GetMapping("/comments")
    ResponseEntity<List<GuildPostCommentResponseDto>> getAllGuildPostComments();

    @Operation(summary = "댓글 수정 (댓글 작성자/어드민)", description = "작성한 댓글을 수정합니다.")
    @PutMapping("/comments/{commentId}")
    ResponseEntity<Void> updateGuildPostComment(
            @Parameter(description = "댓글 ID", example = "1") @PathVariable(name = "commentId") Long commentId,
            @Parameter(description = "댓글 수정 요청 DTO") @RequestBody GuildPostCommentUpdateRequestDto requestDto);

    @Operation(summary = "댓글 삭제 (댓글 작성자/어드민)", description = "작성한 댓글을 삭제합니다.")
    @DeleteMapping("/comments/{commentId}")
    ResponseEntity<Void> deleteGuildPostComment(
            @Parameter(description = "댓글 ID", example = "1") @PathVariable(name = "commentId") Long commentId,
            @Parameter(description = "로그인 사용자 정보", hidden = true) @AuthenticationPrincipal CustomUserDetails member);
}
