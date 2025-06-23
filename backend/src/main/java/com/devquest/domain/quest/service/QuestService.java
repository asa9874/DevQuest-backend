package com.devquest.domain.quest.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.devquest.domain.auth.util.AuthUtil;
import com.devquest.domain.member.model.Member;
import com.devquest.domain.member.repository.MemberRepository;
import com.devquest.domain.quest.dto.requestDto.QuestCreateRequestDto;
import com.devquest.domain.quest.dto.requestDto.QuestUpdateRequestDto;
import com.devquest.domain.quest.dto.responseDto.QuestResponseDto;
import com.devquest.domain.quest.dto.responseDto.QuestWithLikeResponseDto;
import com.devquest.domain.quest.model.Quest;
import com.devquest.domain.quest.model.QuestLike;
import com.devquest.domain.quest.repository.QuestChallengeRepository;
import com.devquest.domain.quest.repository.QuestLikeRepository;
import com.devquest.domain.quest.repository.QuestRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class QuestService {
    private final QuestRepository questRepository;
    private final QuestLikeRepository questLikeRepository;
    private final QuestChallengeRepository questChallengeRepository;
    private final MemberRepository memberRepository;

    public void createQuest(
            QuestCreateRequestDto requestDto,
            Long memberId) {
        
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new IllegalArgumentException("회원을 찾을수없습니다"));
        
        Quest quest = Quest.builder()
                .title(requestDto.title())
                .description(requestDto.description())
                .creater(member)
                .build();
        questRepository.save(quest);
    }

    public QuestWithLikeResponseDto getQuest(Long questId) {
        QuestWithLikeResponseDto responseDto = questRepository.findByIdWithLikeCount(questId)
                .orElseThrow(() -> new IllegalArgumentException("퀘스트를 찾을 수 없습니다"));
        return responseDto;
    }

    public List<QuestWithLikeResponseDto> getAllQuests() {
        List<QuestWithLikeResponseDto> responseDtos = questRepository.findAllWithLikeCount();
        return responseDtos;
    }

    public Page<QuestWithLikeResponseDto> search(
            String title,
            String creatorName,
            Pageable pageable) {
        Page<QuestWithLikeResponseDto> responseDtos = questRepository.searchQuests(title, creatorName, pageable);
        return responseDtos;
    }

    public Page<QuestWithLikeResponseDto> getQuestsByMemberId(
            Long memberId,
            Boolean isCompleted,
            Boolean liked,
            String title,
            Pageable pageable
            ) {
        if(!AuthUtil.isAdminOrEqualMember(memberId)) {
            throw new IllegalArgumentException("권한이 없습니다");
        }
        return null;
    }

    public void likeQuest(Long questId, Long memberId) {
        if(questLikeRepository.existsByMemberIdAndQuestId(memberId, questId)) {
            throw new IllegalArgumentException("이미 좋아요를 누른 퀘스트입니다");
        }
        Quest quest = questRepository.findById(questId)
                .orElseThrow(() -> new IllegalArgumentException("퀘스트를 찾을 수 없습니다"));
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new IllegalArgumentException("회원을 찾을 수 없습니다"));
        QuestLike questLike = QuestLike.builder()
                .quest(quest)
                .member(member)
                .build();
        questLikeRepository.save(questLike);
    }

    public void unlikeQuest(Long questId, Long memberId) {
        QuestLike questLike = questLikeRepository.findByMemberIdAndQuestId(memberId, questId)
                .orElseThrow(() -> new IllegalArgumentException("퀘스트 좋아요를 찾을 수 없습니다"));
        questLikeRepository.delete(questLike);
    }

    public QuestResponseDto updateQuest(
            Long questId,
            QuestUpdateRequestDto requestDto) {
        Quest quest = questRepository.findById(questId)
                .orElseThrow(() -> new IllegalArgumentException("퀘스트를 찾을 수 없습니다"));
        if (!AuthUtil.isAdminOrEqualMember(quest.getCreater().getId())) {
            throw new IllegalArgumentException("권한이 없습니다");
        }
        quest.update(requestDto.title(), requestDto.description());
        questRepository.save(quest);
        return QuestResponseDto.from(quest);
    }

    public void deleteQuest(Long questId) {
        Quest quest = questRepository.findById(questId)
                .orElseThrow(() -> new IllegalArgumentException("퀘스트를 찾을 수 없습니다"));
        if (!AuthUtil.isAdminOrEqualMember(quest.getCreater().getId())) {
            throw new IllegalArgumentException("권한이 없습니다");
        }
        questChallengeRepository.deleteByQuestId(questId);
        questLikeRepository.deleteByQuestId(questId);
        questRepository.delete(quest);
    }
}
