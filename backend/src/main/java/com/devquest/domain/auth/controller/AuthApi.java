package com.devquest.domain.auth.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

import com.devquest.domain.auth.dto.requestDto.AuthLoginRequestDto;
import com.devquest.domain.auth.dto.requestDto.AuthRegisterRequestDto;
import com.devquest.domain.auth.dto.responseDto.AuthLoginResponseDto;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "인증 API", description = "로그인/회원가입/로그아웃 등 인증 관련 API")
public interface AuthApi {
    @Operation(summary = "로그인", description = "아이디/비밀번호로 로그인합니다.")
    ResponseEntity<AuthLoginResponseDto> login(
        @Parameter(description = "로그인 요청 DTO") @RequestBody AuthLoginRequestDto requestDto
    );

    @Operation(summary = "회원가입", description = "새로운 회원을 등록합니다.")
    ResponseEntity<Void> register(
        @Parameter(description = "회원가입 요청 DTO") @RequestBody AuthRegisterRequestDto requestDto
    );

    @Operation(summary = "로그아웃", description = "토큰을 만료시켜 로그아웃합니다.")
    ResponseEntity<Void> logout(
        @Parameter(description = "JWT 토큰", example = "Bearer ...") @RequestHeader(value = "Authorization", required = false) String token
    );
}
