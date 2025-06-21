package com.devquest.domain.quest.service;

import org.springframework.stereotype.Service;

import com.devquest.domain.quest.repository.QuestChallengeRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class QuestChallengeService {
    private final QuestChallengeRepository questChallengeRepository;
}
