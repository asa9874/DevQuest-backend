package com.devquest.domain.member.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.devquest.domain.member.dto.responseDto.MemberResponseDto;
import com.devquest.domain.member.service.Memberservice;
import com.devquest.global.jwt.CustomUserDetails;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/member")
public class MemberController {
    private final Memberservice memberService;

    @GetMapping("/me")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<MemberResponseDto> getMyInfo(@AuthenticationPrincipal CustomUserDetails member) {
        MemberResponseDto responseDto = memberService.getMember(member.getId());
        return ResponseEntity.ok().body(responseDto);
    }
}
