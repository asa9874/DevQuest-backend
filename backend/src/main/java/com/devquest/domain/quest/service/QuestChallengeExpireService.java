package com.devquest.domain.quest.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.devquest.domain.quest.model.QuestChallenge;
import com.devquest.domain.quest.model.QuestStatus;
import com.devquest.domain.quest.repository.QuestChallengeRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class QuestChallengeExpireService {

    private final QuestChallengeRepository questChallengeRepository;
    private static final int EXPIRE_DAYS = 5;

    /**
     * 5일이 지난 진행중인 퀘스트 챌린지를 자동으로 실패 처리
     */
    @Scheduled(cron = "0 0 0 * * *")
    @Transactional
    public void expireOldQuestChallenges() {
        log.info("퀘스트 챌린지 만료 처리 배치 시작");

        LocalDateTime expireDateTime = LocalDateTime.now().minusDays(EXPIRE_DAYS);

        List<QuestChallenge> expiredChallenges = questChallengeRepository
                .findExpiredChallenges(QuestStatus.IN_PROGRESS, expireDateTime);

        if (expiredChallenges.isEmpty()) {
            log.info("만료될 퀘스트 챌린지가 없습니다.");
            return;
        }

        // 만료된 챌린지들을 실패 처리
        int expiredCount = 0;
        for (QuestChallenge challenge : expiredChallenges) {
            try {
                challenge.fail();
                questChallengeRepository.save(challenge);
                expiredCount++;

                log.info("퀘스트 챌린지 만료 처리 완료 - ID: {}, 회원 ID: {}, 퀘스트 ID: {}",
                    challenge.getId(),
                    challenge.getMember().getId(),
                    challenge.getQuest().getId());

            } catch (Exception e) {
                log.error("퀘스트 챌린지 만료 처리 중 오류 발생 - ID: {}, 오류: {}",
                    challenge.getId(), e.getMessage(), e);
            }
        }

        log.info("퀘스트 챌린지 만료 처리 배치 완료 - 처리된 챌린지 수: {} / 전체: {}",
            expiredCount, expiredChallenges.size());
    }

}
