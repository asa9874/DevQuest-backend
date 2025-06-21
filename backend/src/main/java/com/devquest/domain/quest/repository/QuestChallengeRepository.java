package com.devquest.domain.quest.repository;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.devquest.domain.quest.dto.responseDto.QuestChallengeResponseDto;
import com.devquest.domain.quest.model.QuestChallenge;
import com.devquest.domain.quest.model.QuestStatus;

public interface QuestChallengeRepository extends JpaRepository<QuestChallenge, Long> {
    void deleteByMemberId(Long memberId);

    void deleteByQuestId(Long questId);

    boolean existsByQuestIdAndMemberIdAndStatus(Long questId, Long memberId, QuestStatus status);

    // 회원별 챌린지 목록
    @Query("""
                SELECT new com.devquest.domain.quest.dto.responseDto.QuestChallengeResponseDto(
                    qc.id,
                    q.id,
                    q.title,
                    q.description,
                    qc.status,
                    q.creater.name,
                    q.creater.id,
                    m.name,
                    m.id,
                    qc.startedAt,
                    qc.endAt
                )
                FROM QuestChallenge qc
                JOIN qc.quest q
                JOIN qc.member m
                WHERE qc.member.id = :memberId
                AND (:status IS NULL OR qc.status = :status)
                AND (:title IS NULL OR q.title LIKE CONCAT('%', :title, '%'))
                ORDER BY qc.id DESC
            """)
    List<QuestChallengeResponseDto> findDtoByMemberIdWithFilter(@Param("memberId") Long memberId,
            @Param("status") QuestStatus status,
            @Param("title") String title,
            Pageable pageable);

    // 퀘스트별 챌린지 목록
    @Query("""
                SELECT new com.devquest.domain.quest.dto.responseDto.QuestChallengeResponseDto(
                    qc.id,
                    q.id,
                    q.title,
                    q.description,
                    qc.status,
                    q.creater.name,
                    q.creater.id,
                    m.name,
                    m.id,
                    qc.startedAt,
                    qc.endAt
                )
                FROM QuestChallenge qc
                JOIN qc.quest q
                JOIN qc.member m
                WHERE qc.quest.id = :questId
                AND (:status IS NULL OR qc.status = :status)
                ORDER BY qc.id DESC
            """)
    List<QuestChallengeResponseDto> findDtoByQuestIdWithFilter(@Param("questId") Long questId,
            @Param("status") QuestStatus status,
            Pageable pageable);
}
