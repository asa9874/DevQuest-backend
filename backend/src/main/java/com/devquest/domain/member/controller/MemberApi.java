package com.devquest.domain.member.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import com.devquest.domain.member.dto.requestDto.MemberUpdatePassswordRequetsDto;
import com.devquest.domain.member.dto.requestDto.MemberUpdateRequestDto;
import com.devquest.domain.member.dto.responseDto.MemberResponseDto;
import com.devquest.global.jwt.CustomUserDetails;

public interface MemberApi {
    // 사용자 자기자신용
    ResponseEntity<MemberResponseDto> getMyProfile(@AuthenticationPrincipal CustomUserDetails member);
    ResponseEntity<Void> deleteMyAccount(@PathVariable(name = "memberId") Long memberId);
    ResponseEntity<Void> updatePassword(@AuthenticationPrincipal CustomUserDetails member, @RequestBody MemberUpdatePassswordRequetsDto requestDto);
    ResponseEntity<MemberResponseDto> updateMember(@PathVariable(name = "memberId") Long memberId, @RequestBody MemberUpdateRequestDto requestDto);
    ResponseEntity<MemberResponseDto> getMemberById(@PathVariable(name = "memberId") Long memberId);
    ResponseEntity<List<MemberResponseDto>> getAllMembers();
}