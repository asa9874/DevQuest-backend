package com.devquest.domain.monster.service;

import java.util.List;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

import com.devquest.domain.member.model.Member;
import com.devquest.domain.member.repository.MemberRepository;
import com.devquest.domain.monster.dto.requestDto.MonsterCreateRequestDto;
import com.devquest.domain.monster.dto.requestDto.MonsterUpdateRequestDto;
import com.devquest.domain.monster.dto.responseDto.MonsterResponseDto;
import com.devquest.domain.monster.model.Monster;
import com.devquest.domain.monster.repository.MonsterRepository;
import com.devquest.domain.monster.util.MonsterQuizValidator;
import com.devquest.global.exception.customException.DuplicateDataException;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MonsterService {
    private final MonsterRepository monsterRepository;
    private final MemberRepository memberRepository;
    private final MonsterQuizValidator monsterValidator;

    public List<MonsterResponseDto> getAllMonsters() {
        return monsterRepository.findAll().stream()
                .map(MonsterResponseDto::from)
                .toList();
    }

    public MonsterResponseDto getMonsterById(Long monsterId) {
        Monster monster = monsterRepository.findById(monsterId)
                .orElseThrow(() -> new EntityNotFoundException("존재하지않는 몬스터입니다."));
        return MonsterResponseDto.from(monster);
    }

    public void createMonster(
            MonsterCreateRequestDto requestDto,
            Long memberId) {

        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new EntityNotFoundException("존재하지않는 회원입니다."));

        if (monsterRepository.existsByName(requestDto.name())) {
            throw new DuplicateDataException("이미 존재하는 몬스터 이름입니다.");
        }

        Monster monster = Monster.builder()
                .name(requestDto.name())
                .description(requestDto.description())
                .difficulty(requestDto.difficulty())
                .required_correct_count(requestDto.requiredCorrectCount())
                .creater(member)
                .build();
        monsterRepository.save(monster);
    }

    public void updateMonster(MonsterUpdateRequestDto requestDto, Long monsterId, Long memberId) {
        Monster monster = monsterRepository.findById(monsterId)
                .orElseThrow(() -> new EntityNotFoundException("존재하지않는 몬스터입니다."));

        if (!monsterValidator.isMonsterOwner(monsterId, memberId)) {
            throw new AccessDeniedException("몬스터를 수정할 권한이 없습니다.");
        }

        if (monsterRepository.existsByName(requestDto.name())) {
            throw new DuplicateDataException("이미 존재하는 몬스터 이름입니다.");
        }

        monster.update(
                requestDto.name(),
                requestDto.description(),
                requestDto.difficulty(),
                requestDto.requiredCorrectCount());
        monsterRepository.save(monster);
    }

    public void deleteMonster(Long monsterId, Long memberId) {
        Monster monster = monsterRepository.findById(monsterId)
                .orElseThrow(() -> new EntityNotFoundException("존재하지않는 몬스터입니다."));

        if (!monsterValidator.isMonsterOwner(monsterId, memberId)) {
            throw new AccessDeniedException("몬스터를 수정할 권한이 없습니다.");
        }

        monsterRepository.delete(monster);
    }

}
