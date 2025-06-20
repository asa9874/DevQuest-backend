package com.devquest.domain.member.dto.responseDto;

import com.devquest.domain.member.model.Member;

public record MemberPublicResponseDto(
    Long id,
    String name
) {
    public static MemberPublicResponseDto from(Member member) {
        return new MemberPublicResponseDto(
            member.getId(),
            member.getName()
        );
    }
}
