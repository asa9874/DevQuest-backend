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
    
    public boolean isQuestOwner(Long questId, Long memberId) {
        return AuthUtil.isAdmin() || 
                questRepository.existsByIdAndCreaterId(questId, memberId);
    }
    
    public boolean isQuestChallengeOwner(Long questChallengeId, Long memberId) {
        return AuthUtil.isAdmin() || 
                questChallengeRepository.existsByIdAndMemberId(questChallengeId, memberId);
    }
}
