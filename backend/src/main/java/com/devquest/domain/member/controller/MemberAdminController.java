package com.devquest.domain.member.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.devquest.domain.member.dto.responseDto.MemberResponseDto;
import com.devquest.domain.member.service.Memberservice;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/members/admin")
@RequiredArgsConstructor
public class MemberAdminController implements MemberAdminApi {
    private final Memberservice memberService;

    @Override
    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<MemberResponseDto>> getAllMembers() {
        List<MemberResponseDto> responseDtos = memberService.getAllMembers();
        return ResponseEntity.ok().body(responseDtos);
    }

    @Override
    @GetMapping("/{memberId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<MemberResponseDto> getMemberById(@PathVariable Long memberId) {
        MemberResponseDto responseDto = memberService.getMember(memberId);
        return ResponseEntity.ok().body(responseDto);
    }
}
