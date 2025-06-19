package com.devquest.domain.member.dto.responseDto;

import com.devquest.domain.member.model.Member;

public record MemberResponseDto(
    Long id,
    String email,
    String name
) {
    public static MemberResponseDto from(Member member) {
        return new MemberResponseDto(
            member.getId(),
            member.getEmail(),
            member.getName()
        );
    }
}
