package com.devquest.domain.member.dto.requestDto;

public record MemberUpdateRequestDto(
    String name
) {
    public MemberUpdateRequestDto {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("이름은 비어 있을 수 없습니다.");
        }
        if (name.length() < 2 || name.length() > 50) {
            throw new IllegalArgumentException("이름은 2자 이상 50자 이하이어야 합니다.");
        }
    }
    
}
