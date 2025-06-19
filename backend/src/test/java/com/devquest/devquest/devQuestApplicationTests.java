package com.devquest.devquest;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.devquest.domain.member.model.Member;
import com.devquest.domain.member.model.Role;
import com.devquest.domain.member.repository.MemberRepository;

@SpringBootTest
class devQuestApplicationTests {

	@Autowired
	private MemberRepository memberRepository;
	@Autowired
	private PasswordEncoder passwordEncoder;

	@Test
	void contextLoads() {
		for (int i = 0; i < 10; i++) {
			Member member = Member.builder()
					.name("tester" + i)
					.password(passwordEncoder.encode("tester" + i))
					.email("tester" + i)
					.role(Role.USER)
					.build();
			memberRepository.save(member);
		}
		Member member = Member.builder()
				.name("admin")
				.password(passwordEncoder.encode("admin123"))
				.email("admin")
				.role(Role.ADMIN)
				.build();
		memberRepository.save(member);
	}

}
