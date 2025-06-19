package com.devquest.domain.member.dto.requestDto;

public record MemberUpdatePassswordRequetsDto(
    String currentPassword,
    String newPassword,
    String confirmNewPassword

) {
    public MemberUpdatePassswordRequetsDto {
        if (currentPassword == null || currentPassword.isBlank() || currentPassword.length() < 6) {
            throw new IllegalArgumentException("현재 비밀번호가 유효하지 않습니다.");
        }
        if (newPassword == null || newPassword.isBlank() || newPassword.length() < 6) {
            throw new IllegalArgumentException("새 비밀번호가 유효하지 않습니다.");
        }
        if (confirmNewPassword == null || confirmNewPassword.isBlank() || confirmNewPassword.length() < 6) {
            throw new IllegalArgumentException("확인 비밀번호가 유효하지 않습니다.");
        }
    }
}
