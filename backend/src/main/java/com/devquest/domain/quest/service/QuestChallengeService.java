package com.devquest.domain.quest.service;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.security.access.AccessDeniedException;
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
import com.devquest.domain.quest.util.QuestValidator;
import com.devquest.global.exception.customException.DuplicateDataException;
import jakarta.persistence.EntityNotFoundException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class QuestChallengeService {
    private final QuestChallengeRepository questChallengeRepository;
    private final QuestRepository questRepository;
    private final MemberRepository memberRepository;
    private final QuestValidator questValidator;

    public void createQuestChallenge(QuestChallengeCreateRequestDto requestDto) {
        if (!AuthUtil.isAdminOrEqualMember(requestDto.memberId())) {
            throw new AccessDeniedException("권한이 없습니다");
        }
        if (questChallengeRepository.existsByQuestIdAndMemberIdAndStatus(requestDto.questId(), requestDto.memberId(),
                QuestStatus.IN_PROGRESS)) {
            throw new DuplicateDataException("이미 진행중인 퀘스트입니다");
        }
        Quest quest = questRepository.findById(requestDto.questId())
                .orElseThrow(() -> new EntityNotFoundException("존재하지 않는 퀘스트입니다"));
        Member member = memberRepository.findById(requestDto.memberId())
                .orElseThrow(() -> new EntityNotFoundException("존재하지 않는 회원입니다"));

        QuestChallenge questChallenge = QuestChallenge.builder()
                .quest(quest)
                .member(member)
                .build();
        questChallengeRepository.save(questChallenge);
    }

    public QuestChallengeResponseDto getQuestChallenge(Long questChallengeId) {
        QuestChallenge questChallenge = questChallengeRepository.findById(questChallengeId)
                .orElseThrow(() -> new EntityNotFoundException("존재하지 않는 챌린지입니다"));
        if (!questValidator.isQuestChallengeOwner(questChallengeId, AuthUtil.getCurrentMemberId())) {
            throw new AccessDeniedException("권한이 없습니다");
        }
        return QuestChallengeResponseDto.from(questChallenge);
    }

    public List<QuestChallengeResponseDto> getQuestChallengesByMemberId(
            Long memberId, QuestStatus status, String title, Pageable pageable) {
        if (!AuthUtil.isAdminOrEqualMember(memberId)) {
            throw new AccessDeniedException("권한이 없습니다");
        }
        return questChallengeRepository.findDtoByMemberIdWithFilter(memberId, status, title, pageable);
    }

    public List<QuestChallengeResponseDto> getQuestChallengesByQuestId(
            Long questId, QuestStatus status, Pageable pageable) {
        if (!questRepository.existsById(questId)) {
            throw new EntityNotFoundException("존재하지 않는 퀘스트입니다");
        }
        if (!questValidator.isQuestOwner(questId, AuthUtil.getCurrentMemberId())) {
            throw new AccessDeniedException("권한이 없습니다");
        }
        return questChallengeRepository.findDtoByQuestIdWithFilter(questId, status, pageable);
    }

    public void completeQuestChallenge(Long questChallengeId) {
        QuestChallenge questChallenge = questChallengeRepository.findById(questChallengeId)
                .orElseThrow(() -> new EntityNotFoundException("존재하지 않는 챌린지입니다"));
        
        if (!questChallenge.isInProgress()) {
            throw new IllegalStateException("진행중인 챌린지가 아닙니다");
        }
        
        // 만료된 퀘스트인지 확인
        if (questChallenge.isExpired()) {
            // 만료된 퀘스트는 자동으로 실패 처리
            questChallenge.fail();
            questChallengeRepository.save(questChallenge);
            throw new IllegalStateException("퀘스트가 만료되어 자동으로 실패 처리되었습니다");
        }
        
        if (!questValidator.isQuestChallengeOwner(questChallengeId, AuthUtil.getCurrentMemberId())) {
            throw new AccessDeniedException("권한이 없습니다");
        }
        
        questChallenge.complete();
        questChallengeRepository.save(questChallenge);
    }

    public void failQuestChallenge(Long questChallengeId) {
        QuestChallenge questChallenge = questChallengeRepository.findById(questChallengeId)
                .orElseThrow(() -> new EntityNotFoundException("존재하지 않는 챌린지입니다"));
        if (!questChallenge.isInProgress()) {
            throw new IllegalStateException("진행중인 챌린지가 아닙니다");
        }
        if (!questValidator.isQuestChallengeOwner(questChallengeId, AuthUtil.getCurrentMemberId())) {
            throw new AccessDeniedException("권한이 없습니다");
        }
        questChallenge.fail();
        questChallengeRepository.save(questChallenge);
    }

}
