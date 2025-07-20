package com.devquest.domain.skill.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.devquest.domain.skill.dto.responseDto.MemberSkillResponseDto;
import com.devquest.domain.skill.model.MemberSkill;

public interface MemberSkillRepository extends JpaRepository<MemberSkill, Long> {
    boolean existsByMemberIdAndSkillId(Long memberId, Long skillId);

    @Query("""
            SELECT new com.devquest.domain.skill.dto.responseDto.MemberSkillResponseDto(
                ms.id,
                ms.member.id,
                ms.skill.id,
                ms.skill.name,
                ms.skill.description,
                ms.acquiredAt
            )
            FROM MemberSkill ms
            WHERE ms.member.id = :memberId
            """)
    List<MemberSkillResponseDto> findAllDtoByMemberId(
            @Param("memberId") Long memberId);

    @Query("""
            SELECT new com.devquest.domain.skill.dto.responseDto.MemberSkillResponseDto(
                ms.id,
                ms.member.id,
                ms.skill.id,
                ms.skill.name,
                ms.skill.description,
                ms.acquiredAt
            )
            FROM MemberSkill ms
            WHERE ms.member.id = :memberId AND ms.skill.id = :skillId
            """)
    Optional<MemberSkillResponseDto> findDtoByMemberIdAndSkillId(
            @Param("memberId") Long memberId,
            @Param("skillId") Long skillId);

    Optional<MemberSkill> findByMemberIdAndSkillId(Long memberId, Long skillId);
}
