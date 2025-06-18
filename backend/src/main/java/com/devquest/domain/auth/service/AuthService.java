package com.devquest.domain.auth.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.devquest.domain.auth.dto.requestDto.AuthLoginRequestDto;
import com.devquest.domain.auth.dto.requestDto.AuthRegisterRequestDto;
import com.devquest.domain.auth.dto.responseDto.AuthLoginResponseDto;
import com.devquest.domain.member.model.Member;
import com.devquest.domain.member.model.Role;
import com.devquest.domain.member.repository.MemberRepository;
import com.devquest.global.jwt.JwtTokenProvider;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final PasswordEncoder passwordEncoder;
    private final MemberRepository memberRepository;
    private final JwtTokenProvider jwtTokenProvider;

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
                .role(Role.USER)
                .build();
        memberRepository.save(member);
    }

    public AuthLoginResponseDto login(AuthLoginRequestDto requestDto) {
        Member member =memberRepository.findByEmail(requestDto.email())
                .orElseThrow(() -> new IllegalArgumentException("회원을 찾을 수 없습니다."));

        if (!passwordEncoder.matches(requestDto.password(), member.getPassword())) {
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }

        String token = jwtTokenProvider.createToken(member.getEmail(), member.getRole().name(), member.getId());
        return new AuthLoginResponseDto(token);
    }
}
