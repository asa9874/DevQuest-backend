package com.devquest.domain.guild.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import com.devquest.domain.guild.dto.requestDto.GuildPostCreateRequestDto;
import com.devquest.domain.guild.dto.requestDto.GuildPostUpdateRequestDto;
import com.devquest.domain.guild.dto.responseDto.GuildPostResponseDto;
import com.devquest.global.jwt.CustomUserDetails;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "길드 게시글 API", description = "길드 게시글 관련 API")
public interface GuildPostApi {
    @Operation(summary = "길드 게시글 생성 (유저/어드민)", description = "길드에 게시글을 작성합니다. (로그인 필요)")
    ResponseEntity<Void> createGuildPost(
        @Parameter(description = "게시글 생성 요청 DTO") @RequestBody GuildPostCreateRequestDto requestDto,
        @Parameter(description = "로그인 사용자 정보", hidden = true) @AuthenticationPrincipal CustomUserDetails member
    );

    @Operation(summary = "길드 게시글 조회 (유저/어드민)", description = "게시글 ID로 게시글을 조회합니다.")
    ResponseEntity<GuildPostResponseDto> getGuildPost(
        @Parameter(description = "게시글 ID", example = "1") @PathVariable(name = "postId") Long postId
    );

    @Operation(summary = "길드별 게시글 목록 조회 (유저/어드민)", description = "특정 길드의 게시글 목록을 조회합니다.")
    ResponseEntity<List<GuildPostResponseDto>> getGuildPostsByGuildId(
        @Parameter(description = "길드 ID", example = "1") @PathVariable(name = "guildId") Long guildId
    );

    @Operation(summary = "전체 게시글 목록 조회 (어드민)", description = "관리자 권한으로 전체 게시글 목록을 조회합니다.")
    ResponseEntity<List<GuildPostResponseDto>> getGuildPosts();

    @Operation(summary = "길드 게시글 수정 (유저/어드민)", description = "게시글을 수정합니다.")
    ResponseEntity<Void> updateGuildPost(
        @Parameter(description = "게시글 ID", example = "1") @PathVariable(name = "postId") Long postId,
        @Parameter(description = "게시글 수정 요청 DTO") @RequestBody GuildPostUpdateRequestDto requestDto
    );

    @Operation(summary = "길드 게시글 삭제 (유저/어드민)", description = "게시글을 삭제합니다.")
    ResponseEntity<Void> deleteGuildPost(
        @Parameter(description = "게시글 ID", example = "1") @PathVariable(name = "postId") Long postId
    );
}
