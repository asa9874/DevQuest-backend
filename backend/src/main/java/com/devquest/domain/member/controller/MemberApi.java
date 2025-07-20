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

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "회원 API", description = "회원 관련 API")
public interface MemberApi {
    @Operation(summary = "내 프로필 조회 (자기자신)", description = "로그인한 사용자의 프로필 정보를 조회합니다.")
    ResponseEntity<MemberResponseDto> getMyProfile(
        @Parameter(description = "로그인 사용자 정보", hidden = true) @AuthenticationPrincipal CustomUserDetails member);

    @Operation(summary = "회원 탈퇴 (자기자신 or 어드민)", description = "특정 회원의 계정을 삭제합니다.")
    ResponseEntity<Void> deleteMyAccount(
        @Parameter(description = "회원 ID", example = "1") @PathVariable(name = "memberId") Long memberId);

    @Operation(summary = "비밀번호 변경 (자기자신)", description = "로그인한 사용자의 비밀번호를 변경합니다.")
    ResponseEntity<Void> updatePassword(
        @Parameter(description = "로그인 사용자 정보", hidden = true) @AuthenticationPrincipal CustomUserDetails member,
        @Parameter(description = "비밀번호 변경 요청 DTO") @RequestBody MemberUpdatePassswordRequetsDto requestDto);

    @Operation(summary = "회원 정보 수정 (자기자신 or 어드민)", description = "특정 회원의 정보를 수정합니다.")
    ResponseEntity<MemberResponseDto> updateMember(
        @Parameter(description = "회원 ID", example = "1") @PathVariable(name = "memberId") Long memberId,
        @Parameter(description = "회원 정보 수정 DTO") @RequestBody MemberUpdateRequestDto requestDto);

    @Operation(summary = "회원 조회 (어드민)", description = "특정 회원의 정보를 조회합니다.")
    ResponseEntity<MemberResponseDto> getMemberById(
        @Parameter(description = "회원 ID", example = "1") @PathVariable(name = "memberId") Long memberId);

    @Operation(summary = "전체 회원 목록 조회 (어드민)", description = "모든 회원의 정보를 조회합니다.")
    ResponseEntity<List<MemberResponseDto>> getAllMembers();

    @Operation(summary = "회원 공개 조회 (전체 공개)", description = "특정 회원의 공개 프로필.")
    ResponseEntity<MemberResponseDto> getPublicMemberProfile(
        @Parameter(description = "회원 ID", example = "1") @PathVariable(name = "memberId") Long memberId);
}
