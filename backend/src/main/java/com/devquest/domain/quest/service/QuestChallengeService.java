package com.devquest.domain.quest.service;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.devquest.domain.auth.util.AuthUtil;
import com.devquest.domain.member.model.Member;
import com.devquest.domain.member.repository.MemberRepository;
import com.devquest.domain.quest.dto.requestDto.QuestChallengeCreateRequestDto;
import com.devquest.domain.quest.dto.responseDto.QuestChallengeResponseDto;
import com.devquest.domain.quest.model.Quest;
import com.devquest.domain.quest.model.QuestChallenge;
import com.devquest.domain.quest.model.QuestStatus;
import com.devquest.domain.quest.repository.QuestChallengeRepository;
import com.devquest.domain.quest.repository.QuestRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class QuestChallengeService {
    private final QuestChallengeRepository questChallengeRepository;
    private final QuestRepository questRepository;
    private final MemberRepository memberRepository;

    public void createQuestChallenge(QuestChallengeCreateRequestDto requestDto) {
        if (!AuthUtil.isAdminOrEqualMember(requestDto.memberId())) {
            throw new IllegalArgumentException("권한이 없습니다");
        }
        if (questChallengeRepository.existsByQuestIdAndMemberIdAndStatus(requestDto.questId(), requestDto.memberId(),
                QuestStatus.IN_PROGRESS)) {
            throw new IllegalArgumentException("이미 진행중인 퀘스트입니다");
        }
        Quest quest = questRepository.findById(requestDto.questId())
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 퀘스트입니다"));
        Member member = memberRepository.findById(requestDto.memberId())
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 회원입니다"));

        QuestChallenge questChallenge = QuestChallenge.builder()
                .quest(quest)
                .member(member)
                .build();
        questChallengeRepository.save(questChallenge);
    }

    public QuestChallengeResponseDto getQuestChallenge(Long questChallengeId) {
        QuestChallenge questChallenge = questChallengeRepository.findById(questChallengeId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 챌린지입니다"));
        if (!AuthUtil.isAdminOrEqualMember(questChallenge.getMember().getId())) {
            throw new IllegalArgumentException("권한이 없습니다");
        }
        return QuestChallengeResponseDto.from(questChallenge);
    }

    public List<QuestChallengeResponseDto> getQuestChallengesByMemberId(
            Long memberId, QuestStatus status, String title, Pageable pageable) {
        if (!AuthUtil.isAdminOrEqualMember(memberId)) {
            throw new IllegalArgumentException("권한이 없습니다");
        }
        return questChallengeRepository.findDtoByMemberIdWithFilter(memberId, status, title, pageable);
    }

    public List<QuestChallengeResponseDto> getQuestChallengesByQuestId(
            Long questId, QuestStatus status, Pageable pageable) {
        Quest quest = questRepository.findById(questId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 퀘스트입니다"));
        if (!AuthUtil.isAdminOrEqualMember(quest.getCreater().getId())) {
            throw new IllegalArgumentException("권한이 없습니다");
        }
        return questChallengeRepository.findDtoByQuestIdWithFilter(questId, status, pageable);
    }

    public void completeQuestChallenge(Long questChallengeId) {
        QuestChallenge questChallenge = questChallengeRepository.findById(questChallengeId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 챌린지입니다"));
        if (!questChallenge.isInProgress()) {
            throw new IllegalArgumentException("진행중인 챌린지가 아닙니다");
        }
        if (!AuthUtil.isAdminOrEqualMember(questChallenge.getMember().getId())) {
            throw new IllegalArgumentException("권한이 없습니다");
        }
        questChallenge.complete();
        questChallengeRepository.save(questChallenge);
    }

    public void failQuestChallenge(Long questChallengeId) {
        QuestChallenge questChallenge = questChallengeRepository.findById(questChallengeId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 챌린지입니다"));
        if (!questChallenge.isInProgress()) {
            throw new IllegalArgumentException("진행중인 챌린지가 아닙니다");
        }
        if (!AuthUtil.isAdminOrEqualMember(questChallenge.getMember().getId())) {
            throw new IllegalArgumentException("권한이 없습니다");
        }
        questChallenge.fail();
        questChallengeRepository.save(questChallenge);
    }

}
