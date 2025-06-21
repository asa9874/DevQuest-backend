package com.devquest.devquest;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.devquest.domain.member.model.Member;
import com.devquest.domain.member.model.Role;
import com.devquest.domain.member.repository.MemberRepository;
import com.devquest.domain.quest.model.Quest;
import com.devquest.domain.quest.model.QuestChallenge;
import com.devquest.domain.quest.model.QuestLike;
import com.devquest.domain.quest.repository.QuestChallengeRepository;
import com.devquest.domain.quest.repository.QuestLikeRepository;
import com.devquest.domain.quest.repository.QuestRepository;

@SpringBootTest
class devQuestApplicationTests {

	@Autowired
	private MemberRepository memberRepository;
	@Autowired
	private PasswordEncoder passwordEncoder;
	@Autowired
	private QuestRepository questRepository;
    @Autowired
    private QuestChallengeRepository questChallengeRepository;
    @Autowired
    private QuestLikeRepository questLikeRepository;
    

	@Test
	void contextLoads() {
		for (int i = 0; i < 10; i++) {
			if (!memberRepository.existsByEmail("tester" + i)) {
				Member member = Member.builder()
						.name("tester" + i)
						.password(passwordEncoder.encode("tester" + i))
						.email("tester" + i)
						.role(Role.USER)
						.provider("local")
						.build();
				memberRepository.save(member);
			}
			if (!memberRepository.existsByEmail("admin" + i)) {
				Member admin = Member.builder()
						.name("admin" + i)
						.password(passwordEncoder.encode("admin123" + i))
						.email("admin" + i)
						.role(Role.ADMIN)
						.provider("local")
						.build();
				memberRepository.save(admin);
			}
		}

		List<Member> allMembers = memberRepository.findAll();
		for (int i = 0; i < 30; i++) {
			Member creator = allMembers.get(i % allMembers.size());
			if (!questRepository.existsByTitle("퀘스트" + i)) {
				Quest quest = Quest.builder()
						.title("퀘스트" + i)
						.description("퀘스트 설명" + i)
						.creater(creator)
						.build();
				questRepository.save(quest);
			}
		}
		List<Quest> allQuests = questRepository.findAll();

		for (Member member : allMembers) {
			for (int j = 0; j < 5; j++) {
				Quest quest = allQuests.get((member.getId().intValue() + j) % allQuests.size());
				QuestChallenge challenge = QuestChallenge.builder()
						.quest(quest)
						.member(member)
						.build();
				if (j % 3 == 0) challenge.complete();
				if (j % 3 == 1) challenge.fail();
				questChallengeRepository.save(challenge);
				QuestLike like = QuestLike.builder()
						.quest(quest)
						.member(member)
						.build();
				questLikeRepository.save(like);
			}
		}
	}

}
