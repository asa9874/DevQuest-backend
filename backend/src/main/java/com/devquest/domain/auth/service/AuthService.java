package com.devquest.domain.auth.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.devquest.domain.auth.dto.requestDto.AuthLoginRequestDto;
import com.devquest.domain.auth.dto.requestDto.AuthRegisterRequestDto;
import com.devquest.domain.auth.dto.responseDto.AuthLoginResponseDto;
import com.devquest.domain.member.model.Member;
import com.devquest.domain.member.repository.MemberRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final PasswordEncoder passwordEncoder;
    private final MemberRepository memberRepository;

    public void register(AuthRegisterRequestDto requestDto) {
        if(memberRepository.existsByEmail(requestDto.email())) {
            throw new IllegalArgumentException("이미 존재하는 이메일입니다.");
        }
        if(memberRepository.existsByName(requestDto.name())) {
            throw new IllegalArgumentException("이미 존재하는 이름입니다.");
        }
        Member member = Member.builder()
                .email(requestDto.email())
                .password(passwordEncoder.encode(requestDto.password()))
                .name(requestDto.name())
                .build();
        memberRepository.save(member);
    }

    public AuthLoginResponseDto login(AuthLoginRequestDto requestDto) {
        return new AuthLoginResponseDto();
    }
}
