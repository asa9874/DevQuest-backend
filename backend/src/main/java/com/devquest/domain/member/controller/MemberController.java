package com.devquest.domain.member.controller;

import java.util.List;

import jakarta.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.devquest.domain.member.dto.requestDto.MemberUpdatePassswordRequetsDto;
import com.devquest.domain.member.dto.requestDto.MemberUpdateRequestDto;
import com.devquest.domain.member.dto.responseDto.MemberResponseDto;
import com.devquest.domain.member.service.Memberservice;
import com.devquest.global.jwt.CustomUserDetails;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/members")
public class MemberController implements MemberApi {
    private final Memberservice memberService;

    @GetMapping("/me")
    @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
    public ResponseEntity<MemberResponseDto> getMyProfile(@AuthenticationPrincipal CustomUserDetails member) {
        MemberResponseDto responseDto = memberService.getMember(member.getId());
        return ResponseEntity.ok().body(responseDto);
    }

    @PutMapping("/{memberId}")
    @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
    public ResponseEntity<MemberResponseDto> updateMember(
            @PathVariable(name = "memberId") Long memberId,
            @Valid @RequestBody MemberUpdateRequestDto requestDto) {
        MemberResponseDto responseDto = memberService.updateMember(memberId, requestDto);
        return ResponseEntity.ok().body(responseDto);
    }

    @DeleteMapping("/{memberId}")
    @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
    public ResponseEntity<Void> deleteMyAccount(
            @PathVariable(name = "memberId") Long memberId) {
        memberService.deleteMember(memberId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @PutMapping("/password")
    @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
    public ResponseEntity<Void> updatePassword(
            @AuthenticationPrincipal CustomUserDetails member,
            @Valid @RequestBody MemberUpdatePassswordRequetsDto requestDto) {
        memberService.updatePassword(member.getId(), requestDto);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/public/{memberId}")
    public ResponseEntity<MemberResponseDto> getPublicMemberProfile(
            @PathVariable(name = "memberId") Long memberId) {
        MemberResponseDto responseDto = memberService.getMember(memberId);
        return ResponseEntity.ok().body(responseDto);
    }

    // 관리자 전용 API
    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<MemberResponseDto>> getAllMembers() {
        List<MemberResponseDto> responseDtos = memberService.getAllMembers();
        return ResponseEntity.ok().body(responseDtos);
    }

    @GetMapping("/{memberId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<MemberResponseDto> getMemberById(@PathVariable(name = "memberId") Long memberId) {
        MemberResponseDto responseDto = memberService.getMember(memberId);
        return ResponseEntity.ok().body(responseDto);
    }
}
