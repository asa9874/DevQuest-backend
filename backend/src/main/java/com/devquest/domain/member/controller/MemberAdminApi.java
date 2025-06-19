package com.devquest.domain.member.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.devquest.domain.member.dto.responseDto.MemberResponseDto;

public interface MemberAdminApi {
    //관리자용
    ResponseEntity<MemberResponseDto> getMemberById(Long memberId);
    ResponseEntity<List<MemberResponseDto>> getAllMembers();
}