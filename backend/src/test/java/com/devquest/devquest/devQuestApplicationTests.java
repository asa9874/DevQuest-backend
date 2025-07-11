package com.devquest.devquest;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.devquest.domain.guild.model.Guild;
import com.devquest.domain.guild.model.GuildMember;
import com.devquest.domain.guild.model.GuildMemberRole;
import com.devquest.domain.guild.model.GuildMemberStatus;
import com.devquest.domain.guild.model.GuildPost;
import com.devquest.domain.guild.model.GuildPostComment;
import com.devquest.domain.guild.repository.GuildMemberRepository;
import com.devquest.domain.guild.repository.GuildPostCommentRepository;
import com.devquest.domain.guild.repository.GuildPostRepository;
import com.devquest.domain.guild.repository.GuildRepository;
import com.devquest.domain.guildChat.model.GuildChatRoom;
import com.devquest.domain.guildChat.repository.GuildChatRoomRepository;
import com.devquest.domain.member.model.Member;
import com.devquest.domain.member.model.Role;
import com.devquest.domain.member.repository.MemberRepository;
import com.devquest.domain.monster.model.Monster;
import com.devquest.domain.monster.model.Quiz;
import com.devquest.domain.monster.repository.MonsterRepository;
import com.devquest.domain.monster.repository.QuizRepository;
import com.devquest.domain.quest.model.Quest;
import com.devquest.domain.quest.model.QuestChallenge;
import com.devquest.domain.quest.model.QuestLike;
import com.devquest.domain.quest.repository.QuestChallengeRepository;
import com.devquest.domain.quest.repository.QuestLikeRepository;
import com.devquest.domain.quest.repository.QuestRepository;
import com.devquest.domain.skill.model.Skill;
import com.devquest.domain.skill.repository.SkillRepository;

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
    @Autowired
    private GuildRepository guildRepository;
    @Autowired
    private GuildMemberRepository guildMemberRepository;
    @Autowired
    private GuildPostRepository guildPostRepository;
    @Autowired
    private GuildPostCommentRepository guildPostCommentRepository;
    @Autowired
    private GuildChatRoomRepository guildChatRoomRepository;
    @Autowired
    private QuizRepository quizRepository;
    @Autowired
    private MonsterRepository monsterRepository;
    @Autowired
    private SkillRepository skillRepository;

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
                if (j % 3 == 0)
                    challenge.complete();
                if (j % 3 == 1)
                    challenge.fail();
                questChallengeRepository.save(challenge);
                QuestLike like = QuestLike.builder()
                        .quest(quest)
                        .member(member)
                        .build();
                questLikeRepository.save(like);
            }
        }

        for (int i = 0; i < 5; i++) {
            if (!guildRepository.existsByName("길드" + i)) {
                Member leader = allMembers.get(i % allMembers.size());
                Guild guild = Guild.builder()
                        .name("길드" + i)
                        .description("길드 설명" + i)
                        .leader(leader)
                        .build();
                guildRepository.save(guild);
            }
        }
        List<Guild> allGuilds = guildRepository.findAll();
        for (Guild guild : allGuilds) {
            for (int i = 0; i < allMembers.size(); i++) {
                Member member = allMembers.get(i);
                boolean exists = guildMemberRepository.findAll().stream()
                        .anyMatch(gm -> gm.getGuild().getId().equals(guild.getId())
                                && gm.getMember().getId().equals(member.getId()));
                if (exists)
                    continue;
                GuildMemberRole role = (i % 10 == 0) ? GuildMemberRole.OWNER
                        : (i % 5 == 0) ? GuildMemberRole.ADMIN : GuildMemberRole.MEMBER;
                GuildMemberStatus status = (i % 7 == 0) ? GuildMemberStatus.BANNED
                        : (i % 4 == 0) ? GuildMemberStatus.LEAVED : GuildMemberStatus.ACTIVE;
                GuildMember guildMember = GuildMember.builder()
                        .guild(guild)
                        .member(member)
                        .role(role)
                        .status(status)
                        .build();
                guildMemberRepository.save(guildMember);
            }
        }
        for (Guild guild : allGuilds) {
            for (Member member : allMembers) {
                for (int p = 0; p < 2; p++) {
                    String title = guild.getName() + " - " + member.getName() + "의 게시글 " + p;
                    String content = "테스트 게시글 내용 " + p + " (" + guild.getName() + ", " + member.getName() + ")";
                    GuildPost post = GuildPost.builder()
                            .title(title)
                            .content(content)
                            .guild(guild)
                            .author(member)
                            .build();
                    guildPostRepository.save(post);

                    for (int c = 0; c < 3; c++) {
                        GuildPostComment comment = GuildPostComment.builder()
                                .content("테스트 댓글 내용 " + c + " (" + title + ")")
                                .guildPost(post)
                                .author(member)
                                .build();
                        guildPostCommentRepository.save(comment);
                    }
                }
            }
        }
        for (Guild guild : allGuilds) {
            for (int r = 0; r < 5; r++) { 
                String roomName = guild.getName() + " 채팅방 " + r;
                GuildChatRoom chatRoom = GuildChatRoom.builder()
                        .guild(guild)
                        .title(roomName)
                        .description("테스트 채팅방 설명 " + r + " (" + guild.getName() + ")")
                        .build();
                guildChatRoomRepository.save(chatRoom);
            }
        }
        
        // 퀴즈 데이터 생성
        String[][] quizData = {
            {"Java 기초", "Java의 기본 데이터 타입이 아닌 것은?", "int", "boolean", "String", "double", "3"},
            {"컬렉션 프레임워크", "다음 중 순서가 있는 컬렉션 인터페이스는?", "Set", "List", "Map", "Queue", "2"},
            {"OOP 개념", "캡슐화에 대한 설명으로 옳은 것은?", "클래스 간 상속 관계를 맺는 것", "데이터와 메소드를 하나로 묶는 것", "하나의 클래스로 여러 객체를 생성하는 것", "클래스를 재사용하는 것", "2"},
            {"예외 처리", "try-catch 구문에서 반드시 필요한 블록은?", "try와 catch", "try와 finally", "catch와 finally", "try만", "1"},
            {"스프링 기초", "스프링의 핵심 개념이 아닌 것은?", "IoC", "AOP", "DI", "MVC", "4"},
            {"데이터베이스", "SQL 문장 중 데이터를 조회하는 명령어는?", "INSERT", "UPDATE", "DELETE", "SELECT", "4"},
            {"네트워크", "HTTP 상태 코드 중 성공을 의미하는 코드 범위는?", "100번대", "200번대", "300번대", "400번대", "2"},
            {"알고리즘", "시간 복잡도가 O(log n)인 정렬 알고리즘은?", "버블 정렬", "퀵 정렬", "삽입 정렬", "선택 정렬", "2"},
            {"자료구조", "LIFO 방식으로 동작하는 자료구조는?", "큐", "스택", "해시맵", "트리", "2"},
            {"개발 도구", "Git에서 현재 브랜치의 작업 상태를 확인하는 명령어는?", "git commit", "git push", "git status", "git merge", "3"}
        };
        
        for (int i = 0; i < quizData.length; i++) {
            String[] data = quizData[i];
            Member creator = allMembers.get(i % allMembers.size());
            
            Quiz quiz = Quiz.builder()
                    .title(data[0])
                    .question(data[1])
                    .option1(data[2])
                    .option2(data[3])
                    .option3(data[4])
                    .option4(data[5])
                    .answer(Integer.parseInt(data[6]))
                    .creater(creator)
                    .build();
            quizRepository.save(quiz);
        }
        
        // Monster 테스트 데이터 생성
        String[][] monsterData = {
            {"자바몬", "자바 코드를 먹고 사는 몬스터", "쉬움", "0"},
            {"스프링드래곤", "스프링 빈을 불처럼 뿜어내는 드래곤", "중간", "0"},
            {"SQL오우거", "데이터베이스를 파괴하는 거대한 오우거", "어려움", "0"},
            {"HTTP유령", "네트워크 패킷 사이에 숨어 있는 유령", "쉬움", "0"},
            {"리액트위치", "컴포넌트로 주문을 거는 마녀", "중간", "0"},
            {"파이썬뱀", "코드를 감싸 죄어오는 뱀", "중간", "0"},
            {"도커고래", "컨테이너를 등에 업고 다니는 고래", "어려움", "0"},
            {"깃허브고스트", "커밋 히스토리에 출몰하는 유령", "쉬움", "0"},
            {"클라우드하이드라", "여러 개의 서버 머리를 가진 괴물", "매우 어려움", "0"},
            {"디버그벌레", "코드 속 버그를 먹고 사는 벌레", "쉬움", "0"}
        };
        
        for (int i = 0; i < monsterData.length; i++) {
            String[] data = monsterData[i];
            Member creator = allMembers.get(i % allMembers.size());
            
            if (!monsterRepository.existsByName(data[0])) {
                Monster monster = Monster.builder()
                        .name(data[0])
                        .description(data[1])
                        .difficulty(data[2])
                        .required_correct_count(Integer.parseInt(data[3]))
                        .creater(creator)
                        .build();
                monsterRepository.save(monster);
            }
        }
        
        // Skill 테스트 데이터 생성
        String[][] skillData = {
            {"자바 기초", "자바 프로그래밍의 기본 개념을 이해합니다."},
            {"객체지향 프로그래밍", "클래스, 상속, 다형성 등 객체지향 개념을 습득합니다."},
            {"스프링 프레임워크", "스프링의 핵심 기능과 구조를 이해합니다."},
            {"JPA 마스터", "JPA를 사용한 데이터 관리 기법을 습득합니다."},
            {"RESTful API 설계", "REST 원칙에 따른 API 설계 방법을 배웁니다."},
            {"프론트엔드 기초", "HTML, CSS, JavaScript의 기본을 이해합니다."},
            {"리액트 개발", "React.js로 SPA 애플리케이션을 개발하는 방법을 배웁니다."},
            {"데이터베이스 설계", "효율적인 데이터베이스 스키마를 설계하는 방법을 습득합니다."},
            {"클라우드 배포", "AWS, Azure 등의 클라우드 서비스에 애플리케이션을 배포하는 방법을 배웁니다."},
            {"DevOps 실무", "CI/CD 파이프라인 구축 및 운영 방법을 습득합니다."}
        };
        
        for (int i = 0; i < skillData.length; i++) {
            String[] data = skillData[i];
            Member creator = allMembers.get(i % allMembers.size());
            
            if (!skillRepository.existsByName(data[0])) {
                Skill skill = Skill.builder()
                        .name(data[0])
                        .description(data[1])
                        .creater(creator)
                        .build();
                skillRepository.save(skill);
            }
        }
    }
}