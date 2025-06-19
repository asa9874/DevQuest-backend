package com.devquest.domain.member.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;

import com.devquest.domain.member.dto.responseDto.MemberResponseDto;
import com.devquest.global.jwt.CustomUserDetails;

public interface MemberApi {
    //사용자 자기자신용
    ResponseEntity<MemberResponseDto> getMyProfile(@AuthenticationPrincipal CustomUserDetails member);
    ResponseEntity<MemberResponseDto> updateMyProfile(@AuthenticationPrincipal CustomUserDetails member);
    ResponseEntity<Void> deleteMyAccount(@AuthenticationPrincipal CustomUserDetails member);
}