package com.devquest.domain.member.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
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

    @Override
    @GetMapping
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<MemberResponseDto> getMyProfile(@AuthenticationPrincipal CustomUserDetails member) {
        MemberResponseDto responseDto = memberService.getMember(member.getId());
        return ResponseEntity.ok().body(responseDto);
    }

    @Override
    @PutMapping
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<MemberResponseDto> updateMember(
            @AuthenticationPrincipal CustomUserDetails member,
            @RequestBody MemberUpdateRequestDto requestDto) {
        MemberResponseDto responseDto = memberService.updateMember(member.getId(), requestDto);
        return ResponseEntity.ok().body(responseDto);
    }

    @Override
    @DeleteMapping
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<Void> deleteMyAccount(@AuthenticationPrincipal CustomUserDetails member) {
        memberService.deleteMember(member.getId());
        return ResponseEntity.noContent().build();
    }

    @Override
    @PutMapping("/password")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<Void> updateMyPassword(@AuthenticationPrincipal CustomUserDetails member,
            @RequestBody MemberUpdatePassswordRequetsDto requestDto) {
        memberService.updatePassword(member.getId(),requestDto);
        return ResponseEntity.noContent().build();
    }
}
