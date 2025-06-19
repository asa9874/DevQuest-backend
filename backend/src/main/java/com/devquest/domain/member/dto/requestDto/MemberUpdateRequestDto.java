package com.devquest.domain.member.dto.requestDto;

public record MemberUpdateRequestDto(
    String name
) {
    public MemberUpdateRequestDto {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("이름은 비어 있을 수 없습니다.");
        }
    }
    
}
