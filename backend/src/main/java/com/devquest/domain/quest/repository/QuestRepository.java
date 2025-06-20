package com.devquest.domain.quest.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.devquest.domain.quest.dto.responseDto.QuestResponseDto;
import com.devquest.domain.quest.model.Quest;

public interface QuestRepository extends JpaRepository<Quest, Long> {
    boolean existsByTitle(String title);

    @Query("""
            SELECT new com.devquest.domain.quest.dto.responseDto.QuestResponseDto(
                q.id, q.title, q.description,q.creater.name, q.creater.id, COUNT(ql), q.createdAt)
            FROM Quest q
            LEFT JOIN QuestLike ql ON q.id = ql.quest.id
            WHERE q.id = :questId
            GROUP BY q
            """)
    Optional<QuestResponseDto> findByIdWithLikeCount(@Param("questId") Long questId);

    @Query("""
            SELECT new com.devquest.domain.quest.dto.responseDto.QuestResponseDto(
                q.id, q.title, q.description,q.creater.name, q.creater.id, COUNT(ql), q.createdAt)
            FROM Quest q
            LEFT JOIN QuestLike ql ON q.id = ql.quest.id
            GROUP BY q
            """)
    List<QuestResponseDto> findAllWithLikeCount();

   @Query("""
    SELECT new com.devquest.domain.quest.dto.responseDto.QuestResponseDto(
        q.id, q.title, q.description, q.creater.name, q.creater.id, COUNT(ql), q.createdAt
    )
    FROM Quest q
    LEFT JOIN QuestLike ql ON q.id = ql.quest.id
    WHERE (:title IS NULL OR q.title LIKE CONCAT('%', :title, '%'))
      AND (:creatorName IS NULL OR q.creater.name LIKE CONCAT('%', :creatorName, '%'))
    GROUP BY q.id, q.title, q.description, q.creater.name, q.creater.id, q.createdAt
    ORDER BY q.createdAt ASC
""")
Page<QuestResponseDto> searchQuests(
        @Param("title") String title,
        @Param("creatorName") String creatorName,
        Pageable pageable
);

}
