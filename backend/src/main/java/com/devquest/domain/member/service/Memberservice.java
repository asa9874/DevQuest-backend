package com.devquest.domain.member.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.devquest.domain.auth.util.AuthUtil;
import com.devquest.domain.member.dto.requestDto.MemberUpdatePassswordRequetsDto;
import com.devquest.domain.member.dto.requestDto.MemberUpdateRequestDto;
import com.devquest.domain.member.dto.responseDto.MemberResponseDto;
import com.devquest.domain.member.model.Member;
import com.devquest.domain.member.repository.MemberRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class Memberservice {
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    public List<MemberResponseDto> getAllMembers() {
        return memberRepository.findAll().stream()
                .map(MemberResponseDto::from)
                .collect(Collectors.toList());
    }

    public MemberResponseDto getMember(Long memberId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new IllegalArgumentException("Member not found"));
        return MemberResponseDto.from(member);
    }

    public MemberResponseDto updateMember(Long memberId, MemberUpdateRequestDto requestDto) {
        if(!AuthUtil.isAdminOrEqualMember(memberId)) {
            throw new IllegalArgumentException("권한이 없습니다");
        }
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new IllegalArgumentException("Member not found"));
        member.updateName(requestDto.name());
        memberRepository.save(member);
        return MemberResponseDto.from(member);
    }

    public void deleteMember(Long memberId) {
        if(!AuthUtil.isAdminOrEqualMember(memberId)) {
            throw new IllegalArgumentException("권한이 없습니다");
        }
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new IllegalArgumentException("Member not found"));
        memberRepository.delete(member);
    }

    public void updatePassword(Long memberId, MemberUpdatePassswordRequetsDto requestDto) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new IllegalArgumentException("Member not found"));
        if (!passwordEncoder.matches(requestDto.currentPassword(), member.getPassword())) {
            throw new IllegalArgumentException("현재비밀번호가 일치하지않습니다.");
        }
        if (!requestDto.newPassword().equals(requestDto.confirmNewPassword())) {
            throw new IllegalArgumentException("새 비밀번호와 확인 비밀번호가 일치하지 않습니다.");
        }
        if (requestDto.newPassword().equals(member.getPassword())) {
            throw new IllegalArgumentException("새 비밀번호가 현재 비밀번호와 동일합니다.");
        }
        member.updatePassword(passwordEncoder.encode(requestDto.newPassword()));
        memberRepository.save(member);
    }
}
