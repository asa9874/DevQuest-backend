package com.devquest.domain.quest.dto.requestDto;

public record QuestChallengeCreateRequestDto(
        Long memberId,
        Long questId
) {
    public QuestChallengeCreateRequestDto {
        if (memberId == null || memberId <= 0) {
            throw new IllegalArgumentException("멤버 ID는 필수 입력값입니다.");
        }
        if (questId == null || questId <= 0) {
            throw new IllegalArgumentException("퀘스트 ID는 필수 입력값입니다.");
        }
    }
}
