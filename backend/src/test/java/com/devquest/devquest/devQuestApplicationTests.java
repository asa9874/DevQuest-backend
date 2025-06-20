package com.devquest.devquest;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.devquest.domain.member.model.Member;
import com.devquest.domain.member.model.Role;
import com.devquest.domain.member.repository.MemberRepository;
import com.devquest.domain.quest.model.Quest;
import com.devquest.domain.quest.repository.QuestRepository;

@SpringBootTest
class devQuestApplicationTests {

	@Autowired
	private MemberRepository memberRepository;
	@Autowired
	private PasswordEncoder passwordEncoder;
	@Autowired
	private QuestRepository questRepository;

	@Test
	void contextLoads() {
		for (int i = 0; i < 10; i++) {
			if (memberRepository.existsByEmail("tester" + i)) {
				continue;
			}
			Member member = Member.builder()
					.name("tester" + i)
					.password(passwordEncoder.encode("tester" + i))
					.email("tester" + i)
					.role(Role.USER)
					.build();
			memberRepository.save(member);
		}
		if (!memberRepository.existsByEmail("admin")) {
			Member member = Member.builder()
					.name("admin")
					.password(passwordEncoder.encode("admin123"))
					.email("admin")
					.role(Role.ADMIN)
					.build();
			memberRepository.save(member);
		}

		// Quest 부분
		Member member = memberRepository.findByEmail("admin")
				.orElseThrow(() -> new RuntimeException("없넹"));
		for (int i = 0; i < 10; i++) {
			if (questRepository.existsByTitle("퀘스트" + i)) {
				continue;
			}
			Quest quest = Quest.builder()
					.title("퀘스트" + i)
					.description("퀘스트 설명" + i)
					.creater(member)
					.build();
			questRepository.save(quest);
		}
	}

}
