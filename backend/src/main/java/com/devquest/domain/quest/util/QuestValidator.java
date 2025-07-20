package com.devquest.domain.quest.util;

import org.springframework.stereotype.Component;

import com.devquest.domain.auth.util.AuthUtil;
import com.devquest.domain.quest.repository.QuestChallengeRepository;
import com.devquest.domain.quest.repository.QuestRepository;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class QuestValidator {
    private final QuestRepository questRepository;
    private final QuestChallengeRepository questChallengeRepository;

    /**
     * 퀘스트의 소유자인지 확인
     */
    public boolean isQuestOwner(Long questId, Long memberId) {
        return AuthUtil.isAdmin() ||
                questRepository.existsByIdAndCreaterId(questId, memberId);
    }

    /**
     * 퀘스트 챌린지의 소유자인지 확인
     */
    public boolean isQuestChallengeOwner(Long questChallengeId, Long memberId) {
        return AuthUtil.isAdmin() ||
                questChallengeRepository.existsByIdAndMemberId(questChallengeId, memberId);
    }
}
