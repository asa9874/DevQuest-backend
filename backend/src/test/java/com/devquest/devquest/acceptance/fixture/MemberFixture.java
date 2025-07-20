package com.devquest.devquest.acceptance.fixture;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.devquest.domain.member.model.Member;
import com.devquest.domain.member.model.Role;
import com.devquest.domain.member.repository.MemberRepository;

@Component
@SuppressWarnings("NonAsciiCharacters")
public class MemberFixture {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    public MemberFixture(MemberRepository memberRepository, PasswordEncoder passwordEncoder) {
        this.memberRepository = memberRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public Member 김개발() {
        String email = "kimdev@example.com";
        if (!memberRepository.existsByEmail(email)) {
            Member member = Member.builder()
                    .name("김개발")
                    .password(passwordEncoder.encode("kimdev123"))
                    .email(email)
                    .role(Role.USER)
                    .provider("local")
                    .build();
            return memberRepository.save(member);
        } else {
            return memberRepository.findByEmail(email).orElseThrow();
        }
    }

    public Member 이코딩() {
        String email = "leecoding@example.com";
        if (!memberRepository.existsByEmail(email)) {
            Member member = Member.builder()
                    .name("이코딩")
                    .password(passwordEncoder.encode("leecoding123"))
                    .email(email)
                    .role(Role.USER)
                    .provider("local")
                    .build();
            return memberRepository.save(member);
        } else {
            return memberRepository.findByEmail(email).orElseThrow();
        }
    }

    public Member 박관리자() {
        String email = "parkadmin@example.com";
        if (!memberRepository.existsByEmail(email)) {
            Member member = Member.builder()
                    .name("박관리자")
                    .password(passwordEncoder.encode("parkadmin123"))
                    .email(email)
                    .role(Role.ADMIN)
                    .provider("local")
                    .build();
            return memberRepository.save(member);
        } else {
            return memberRepository.findByEmail(email).orElseThrow();
        }
    }
}
