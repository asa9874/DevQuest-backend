package com.devquest.domain.member.service;

import org.springframework.stereotype.Service;

import com.devquest.domain.member.dto.responseDto.MemberResponseDto;
import com.devquest.domain.member.model.Member;
import com.devquest.domain.member.repository.MemberRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class Memberservice {
    private final MemberRepository memberRepository;


    public MemberResponseDto getMember(Long memberId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new IllegalArgumentException("Member not found"));
        return MemberResponseDto.from(member);
    }
    
}
