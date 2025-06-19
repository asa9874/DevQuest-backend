package com.devquest.domain.member.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;

import com.devquest.domain.member.dto.responseDto.MemberResponseDto;

public interface MemberAdminApi {
    //관리자용
    ResponseEntity<MemberResponseDto> getMemberById(@PathVariable Long memberId);
    ResponseEntity<List<MemberResponseDto>> getAllMembers();
}