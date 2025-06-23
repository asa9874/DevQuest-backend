package com.devquest.domain.guild.controller;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import com.devquest.domain.guild.dto.requestDto.GuildCreateRequestDto;
import com.devquest.domain.guild.dto.requestDto.GuildUpdateRequestDto;
import com.devquest.domain.guild.dto.responseDto.GuildResponseDto;
import com.devquest.global.jwt.CustomUserDetails;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;

public interface GuildApi {
    @Operation(summary = "길드 생성 (유저/어드민)", description = "새로운 길드를 생성합니다. (로그인 필요)")
    ResponseEntity<Void> createGuild(
        @Parameter(description = "길드 생성 요청 DTO") @RequestBody GuildCreateRequestDto requestDto,
        @Parameter(description = "로그인 사용자 정보", hidden = true) @AuthenticationPrincipal CustomUserDetails member
    );

    @Operation(summary = "전체 길드 목록 조회 (어드민)", description = "모든 길드의 정보를 조회합니다.")
    ResponseEntity<List<GuildResponseDto>> getAllGuilds();

    @Operation(summary = "길드 검색 (전체 공개)", description = "이름으로 길드를 검색합니다. name이 없으면 전체 조회.")
    ResponseEntity<Page<GuildResponseDto>> searchGuilds(
        @Parameter(description = "검색할 길드 이름(부분 일치)", required = false) @RequestParam(required = false, name = "name") String name,
        @Parameter(hidden = true) Pageable pageable
    );

    @Operation(summary = "길드 조회 (전체 공개)", description = "특정 길드의 상세 정보를 조회합니다.")
    ResponseEntity<GuildResponseDto> getGuildById(
        @Parameter(description = "길드 ID", example = "1") @PathVariable(name = "guildId") Long guildId
    );

    @Operation(summary = "길드 정보 수정 (길드장/어드민)", description = "특정 길드의 정보를 수정합니다.")
    ResponseEntity<GuildResponseDto> updateGuild(
        @Parameter(description = "길드 정보 수정 DTO") @RequestBody GuildUpdateRequestDto requestDto,
        @Parameter(description = "길드 ID", example = "1") @PathVariable(name = "guildId") Long guildId
    );

    @Operation(summary = "길드 삭제 (길드장/어드민)", description = "특정 길드를 삭제합니다.")
    ResponseEntity<Void> deleteGuild(
        @Parameter(description = "길드 ID", example = "1") @PathVariable(name = "guildId") Long guildId
    );
}