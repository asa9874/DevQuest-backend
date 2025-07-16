
package com.devquest.domain.guild.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.devquest.domain.guild.dto.responseDto.GuildMemberResponseDto;
import com.devquest.domain.guild.dto.responseDto.GuildResponseDto;
import com.devquest.domain.guild.model.GuildMemberRole;
import com.devquest.domain.guild.model.GuildMemberStatus;
import com.devquest.domain.guild.service.GuildMemberService;
import com.devquest.global.jwt.CustomUserDetails;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/guilds")
@RequiredArgsConstructor
public class GuildMemberController  implements GuildMemberApi {
    private final GuildMemberService guildMemberService;

    @GetMapping("/{guildId}/members")
    @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
    public ResponseEntity<List<GuildMemberResponseDto>> getGuildMembersByGuildId(
            @PathVariable(name="guildId") Long guildId,
            @RequestParam(required = false, name = "status") String status,
            @RequestParam(required = false, name = "role") String role,
            @AuthenticationPrincipal CustomUserDetails member) {
        GuildMemberStatus guildMemberStatus = (status == null || status.isBlank()) ? null
                : GuildMemberStatus.valueOf(status);
        GuildMemberRole guildMemberRole = (role == null || role.isBlank()) ? null
                : GuildMemberRole.valueOf(role);
        List<GuildMemberResponseDto> responseDtos = guildMemberService.getGuildMembers(
                guildId, guildMemberStatus, guildMemberRole, member.getId());
        return ResponseEntity.ok(responseDtos);
    }

    // TODO 이거는 Guild 쪽으로 나중에 이동할거같은데
    @GetMapping("/members/{memberId}")
    @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
    public ResponseEntity<List<GuildResponseDto>> getGuildsByMemberId(
            @PathVariable(name = "memberId") Long memberId,
            @RequestParam(required = false, name = "status") String status,
            @RequestParam(required = false, name = "role") String role) {
        GuildMemberStatus guildMemberStatus = (status == null || status.isBlank()) ? null
                : GuildMemberStatus.valueOf(status);
        GuildMemberRole guildMemberRole = (role == null || role.isBlank()) ? null
                : GuildMemberRole.valueOf(role);
        List<GuildResponseDto> responseDtos = guildMemberService.getGuildsByMemberId(
                memberId, guildMemberStatus, guildMemberRole);
        return ResponseEntity.ok(responseDtos);
    }

    @PostMapping("/{guildId}/members")
    @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
    public ResponseEntity<Void> joinGuild(
            @PathVariable(name = "guildId") Long guildId,
            @AuthenticationPrincipal CustomUserDetails member) {
        guildMemberService.joinGuild(guildId, member.getId());
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PutMapping("/{guildId}/members/{memberId}/leave")
    @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
    public ResponseEntity<Void> leaveGuild(
            @PathVariable(name = "guildId") Long guildId,
            @PathVariable(name = "memberId") Long memberId,
            @AuthenticationPrincipal CustomUserDetails member) {
        guildMemberService.leaveGuild(guildId, memberId, member.getId());
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
    

    @PutMapping("/{guildId}/members/{memberId}/role")
    @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
    public ResponseEntity<Void> changeGuildMemberRole(
            @PathVariable(name = "guildId") Long guildId,
            @PathVariable(name = "memberId") Long memberId,
            @RequestParam(name = "role") String role,
            @AuthenticationPrincipal CustomUserDetails member) {
        GuildMemberRole guildMemberRole = (role == null || role.isBlank()) ? null
                : GuildMemberRole.valueOf(role);
        guildMemberService.changeGuildMemberRole(guildId, memberId, guildMemberRole, member.getId());
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @PutMapping("/{guildId}/members/{memberId}/ban")
    @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
    public ResponseEntity<Void> banGuildMember(
            @PathVariable(name = "guildId") Long guildId,
            @PathVariable(name = "memberId") Long memberId,
            @AuthenticationPrincipal CustomUserDetails member) {
        guildMemberService.banGuildMember(guildId, memberId, member.getId());
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @PutMapping("/{guildId}/members/{memberId}/unban")
    @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
    public ResponseEntity<Void> unbanGuildMember(
            @PathVariable(name = "guildId") Long guildId,
            @PathVariable(name = "memberId") Long memberId,
            @AuthenticationPrincipal CustomUserDetails member) {
        guildMemberService.unbanGuildMember(guildId, memberId, member.getId());
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

}