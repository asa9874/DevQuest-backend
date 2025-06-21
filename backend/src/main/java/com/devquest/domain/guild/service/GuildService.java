package com.devquest.domain.guild.service;

import org.springframework.stereotype.Service;

import com.devquest.domain.guild.dto.requestDto.GuildCreateRequestDto;
import com.devquest.domain.guild.model.Guild;
import com.devquest.domain.guild.repository.GuildRepository;
import com.devquest.domain.member.model.Member;
import com.devquest.domain.member.repository.MemberRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class GuildService {
    private final GuildRepository guildRepository;
    private final MemberRepository memberRepository;

    public void createGuild(GuildCreateRequestDto requestDto,
            Long memberId) {
        Member leader = memberRepository.findById(memberId)
                .orElseThrow(() -> new IllegalArgumentException("회원 정보가 없습니다."));
        if (guildRepository.existsByName(requestDto.name())) {
            throw new IllegalArgumentException("이미 존재하는 길드 이름입니다.");
        }
        Guild guild = Guild.builder()
                .name(requestDto.name())
                .description(requestDto.description())
                .leader(leader)
                .build();
        guildRepository.save(guild);
    }
}
