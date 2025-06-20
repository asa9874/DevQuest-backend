package com.devquest.domain.quest.service;

import org.springframework.stereotype.Service;

import com.devquest.domain.member.model.Member;
import com.devquest.domain.member.repository.MemberRepository;
import com.devquest.domain.quest.dto.requestDto.QuestCreateRequestDto;
import com.devquest.domain.quest.model.Quest;
import com.devquest.domain.quest.repository.QuestRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class QuestService {
    private final QuestRepository questRepository;
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
}
