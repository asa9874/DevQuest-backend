package com.devquest.domain.guild.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import com.devquest.domain.guild.dto.responseDto.GuildMemberResponseDto;
import com.devquest.domain.guild.dto.responseDto.GuildResponseDto;
import com.devquest.global.jwt.CustomUserDetails;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "길드 멤버 API", description = "길드 멤버 관련 API")
public interface GuildMemberApi {
    @Operation(summary = "길드 멤버 목록 조회 (유저/어드민)", description = "특정 길드의 멤버 목록을 조회합니다. status/role로 필터링 가능.")
    ResponseEntity<List<GuildMemberResponseDto>> getGuildMembers(
        @Parameter(name = "guildId", description = "길드 ID", example = "1") @PathVariable(name = "guildId") Long guildId,
        @Parameter(name = "status", description = "멤버 상태", example = "ACTIVE", required = false) @RequestParam(required = false, name = "status") String status,
        @Parameter(name = "role", description = "멤버 역할", example = "MEMBER", required = false) @RequestParam(required = false, name = "role") String role,
        @Parameter(name = "member", description = "로그인 사용자 정보", hidden = true) @AuthenticationPrincipal CustomUserDetails member
    );

    @Operation(summary = "내가 속한 길드 목록 조회 (유저/어드민)", description = "특정 회원이 속한 길드 목록을 조회합니다. status/role로 필터링 가능.")
    ResponseEntity<List<GuildResponseDto>> getGuildsByMemberId(
        @Parameter(name = "memberId", description = "회원 ID", example = "1") @PathVariable(name = "memberId") Long memberId,
        @Parameter(name = "status", description = "멤버 상태", example = "ACTIVE", required = false) @RequestParam(required = false, name = "status") String status,
        @Parameter(name = "role", description = "멤버 역할", example = "MEMBER", required = false) @RequestParam(required = false, name = "role") String role
    );

    @Operation(summary = "길드 가입 (유저/어드민)", description = "길드에 가입합니다.")
    ResponseEntity<Void> joinGuild(
        @Parameter(name = "guildId", description = "길드 ID", example = "1") @PathVariable(name = "guildId") Long guildId,
        @Parameter(name = "member", description = "로그인 사용자 정보", hidden = true) @AuthenticationPrincipal CustomUserDetails member
    );

    @Operation(summary = "길드 탈퇴 (유저/어드민)", description = "길드에서 탈퇴합니다.")
    ResponseEntity<Void> leaveGuild(
        @Parameter(name = "guildId", description = "길드 ID", example = "1") @PathVariable(name = "guildId") Long guildId,
        @Parameter(name = "memberId", description = "회원 ID", example = "1") @PathVariable(name = "memberId") Long memberId,
        @Parameter(name = "member", description = "로그인 사용자 정보", hidden = true) @AuthenticationPrincipal CustomUserDetails member
    );

    @Operation(summary = "길드 멤버 역할 변경 (유저/어드민)", description = "길드 멤버의 역할을 변경합니다.")
    ResponseEntity<Void> changeGuildMemberRole(
        @Parameter(name = "guildId", description = "길드 ID", example = "1") @PathVariable(name = "guildId") Long guildId,
        @Parameter(name = "memberId", description = "회원 ID", example = "1") @PathVariable(name = "memberId") Long memberId,
        @Parameter(name = "role", description = "변경할 역할", example = "ADMIN") @RequestParam(name = "role") String role,
        @Parameter(name = "member", description = "로그인 사용자 정보", hidden = true) @AuthenticationPrincipal CustomUserDetails member
    );

    @Operation(summary = "길드 멤버 강제 추방 (유저/어드민)", description = "길드 멤버를 강제 추방(밴)합니다.")
    ResponseEntity<Void> banGuildMember(
        @Parameter(name = "guildId", description = "길드 ID", example = "1") @PathVariable(name = "guildId") Long guildId,
        @Parameter(name = "memberId", description = "회원 ID", example = "1") @PathVariable(name = "memberId") Long memberId,
        @Parameter(name = "member", description = "로그인 사용자 정보", hidden = true) @AuthenticationPrincipal CustomUserDetails member
    );

    @Operation(summary = "길드 멤버 밴 해제 (유저/어드민)", description = "길드 멤버의 밴(추방) 상태를 해제합니다.")
    ResponseEntity<Void> unbanGuildMember(
        @Parameter(name = "guildId", description = "길드 ID", example = "1") @PathVariable(name = "guildId") Long guildId,
        @Parameter(name = "memberId", description = "회원 ID", example = "1") @PathVariable(name = "memberId") Long memberId,
        @Parameter(name = "member", description = "로그인 사용자 정보", hidden = true) @AuthenticationPrincipal CustomUserDetails member
    );
}
