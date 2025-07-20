package com.devquest.domain.test.service;

import java.util.List;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
import com.devquest.domain.monster.repository.MonsterChallengeRepository;
import com.devquest.domain.monster.repository.MonsterQuizRepository;
import com.devquest.domain.monster.repository.MonsterRepository;
import com.devquest.domain.monster.repository.QuizChallengeRepository;
import com.devquest.domain.monster.repository.QuizRepository;
import com.devquest.domain.quest.model.Quest;
import com.devquest.domain.quest.model.QuestChallenge;
import com.devquest.domain.quest.model.QuestLike;
import com.devquest.domain.quest.repository.QuestChallengeRepository;
import com.devquest.domain.quest.repository.QuestLikeRepository;
import com.devquest.domain.quest.repository.QuestRepository;
import com.devquest.domain.skill.model.Skill;
import com.devquest.domain.skill.repository.MemberSkillRepository;
import com.devquest.domain.skill.repository.SkillRepository;
import com.devquest.domain.skill.repository.SkillRequiredMonsterRepository;
import com.devquest.domain.skill.repository.SkillRequiredQuestRepository;
import com.devquest.domain.skill.repository.SkillRequiredSkillRepository;
import com.devquest.domain.test.dto.AdminSignupRequest;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AdminTestService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final QuestRepository questRepository;
    private final QuestChallengeRepository questChallengeRepository;
    private final QuestLikeRepository questLikeRepository;
    private final GuildRepository guildRepository;
    private final GuildMemberRepository guildMemberRepository;
    private final GuildPostRepository guildPostRepository;
    private final GuildPostCommentRepository guildPostCommentRepository;
    private final GuildChatRoomRepository guildChatRoomRepository;
    private final QuizRepository quizRepository;
    private final MonsterRepository monsterRepository;
    private final SkillRepository skillRepository;

    private final SkillRequiredMonsterRepository skillRequiredMonsterRepository;
    private final SkillRequiredQuestRepository skillRequiredQuestRepository;
    private final SkillRequiredSkillRepository skillRequiredSkillRepository;
    private final MemberSkillRepository memberSkillRepository;
    private final MonsterQuizRepository monsterQuizRepository;
    private final QuizChallengeRepository quizChallengeRepository;
    private final MonsterChallengeRepository monsterChallengeRepository;

    @Transactional
    public void generateTestData() {
        generateMembers();
        generateQuests();
        generateGuilds();
        generateQuizzes();
        generateMonsters();
        generateSkills();
    }

    @Transactional
    public void clearAllData() {
        quizChallengeRepository.deleteAll();
        monsterChallengeRepository.deleteAll();

        guildPostCommentRepository.deleteAll();
        guildPostRepository.deleteAll();
        guildChatRoomRepository.deleteAll();
        guildMemberRepository.deleteAll();
        guildRepository.deleteAll();

        questLikeRepository.deleteAll();
        questChallengeRepository.deleteAll();

        memberSkillRepository.deleteAll();
        skillRequiredMonsterRepository.deleteAll();
        skillRequiredQuestRepository.deleteAll();
        skillRequiredSkillRepository.deleteAll();

        monsterQuizRepository.deleteAll();

        questRepository.deleteAll();
        quizRepository.deleteAll();
        monsterRepository.deleteAll();
        skillRepository.deleteAll();

        memberRepository.deleteAll();
    }

    @Transactional
    public void createAdminMember(AdminSignupRequest request) {
        if (memberRepository.existsByEmail(request.getEmail())) {
            throw new IllegalArgumentException("이미 존재하는 이메일입니다: " + request.getEmail());
        }

        Member admin = Member.builder()
                .name(request.getName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Role.ADMIN)
                .provider("local")
                .build();

        memberRepository.save(admin);
    }

    private void generateMembers() {
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
    }

    private void generateQuests() {
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
    }

    private void generateGuilds() {
        List<Member> allMembers = memberRepository.findAll();
        String[][] guildData = {
            {"프론트엔드 마스터즈", "React, Vue, Angular 등 프론트엔드 기술을 함께 공부하고 프로젝트를 진행하는 커뮤니티입니다."},
            {"백엔드 아키텍츠", "Spring, Node.js, Django 등 서버 기술과 아키텍처 설계를 공유하고 토론하는 모임입니다."},
            {"데브옵스 얼라이언스", "CI/CD, 컨테이너화, 클라우드 인프라 관리 기술을 공유하고 실무 경험을 나누는 길드입니다."},
            {"알고리즘 스터디", "코딩 테스트 대비와 알고리즘 문제 해결 능력 향상을 위한 스터디 그룹입니다."},
            {"머신러닝 연구소", "AI와 머신러닝 기술을 연구하고 프로젝트를 진행하는 연구 중심 커뮤니티입니다."},
            {"오픈소스 컨트리뷰터스", "다양한 오픈소스 프로젝트에 기여하고 협업하는 개발자들의 모임입니다."},
            {"모바일 앱 디벨로퍼스", "iOS, Android 앱 개발 기술과 경험을 공유하는 모바일 개발자 중심 길드입니다."},
            {"블록체인 이노베이터스", "블록체인 기술과 웹3 개발에 관심 있는 개발자들의 혁신적인 모임입니다."},
            {"게임 개발 길드", "게임 엔진과 게임 개발 기술을 공유하고 함께 게임을 만드는 창작자 모임입니다."},
            {"UX/UI 디자인 크루", "개발자와 디자이너가 함께 사용자 경험과 인터페이스 디자인을 연구하는 협업 커뮤니티입니다."}
        };

        for (int i = 0; i < guildData.length; i++) {
            String[] data = guildData[i];
            Member leader = allMembers.get(i % allMembers.size());

            if (!guildRepository.existsByName(data[0])) {
                Guild guild = Guild.builder()
                        .name(data[0])
                        .description(data[1])
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
                    String title = getRandomPostTitle(guild.getName(), p);
                    String content = getRandomPostContent(guild.getName(), p);
                    GuildPost post = GuildPost.builder()
                            .title(title)
                            .content(content)
                            .guild(guild)
                            .author(member)
                            .build();
                    guildPostRepository.save(post);

                    for (int c = 0; c < 3; c++) {
                        GuildPostComment comment = GuildPostComment.builder()
                                .content(getRandomComment(title, c))
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
    }

    private void generateQuizzes() {
        List<Member> allMembers = memberRepository.findAll();
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
    }

    private void generateMonsters() {
        List<Member> allMembers = memberRepository.findAll();
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
    }

    private void generateSkills() {
        List<Member> allMembers = memberRepository.findAll();
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

    private String getRandomPostTitle(String guildName, int index) {
        if (guildName.contains("프론트엔드")) {
            String[] titles = {
                "React 18의 새로운 기능 공유합니다",
                "Vue.js 3 마이그레이션 경험담",
                "CSS-in-JS vs CSS Module 어떤 것이 더 나을까요?",
                "Next.js 앱 라우터 사용기",
                "프론트엔드 성능 최적화 팁"
            };
            return titles[index % titles.length];
        } else if (guildName.contains("백엔드")) {
            String[] titles = {
                "Spring Boot 3.0 마이그레이션 가이드",
                "MSA 설계시 주의점 공유",
                "Node.js vs Spring Boot 성능 비교",
                "데이터베이스 샤딩 전략",
                "GraphQL vs REST API 경험담"
            };
            return titles[index % titles.length];
        } else if (guildName.contains("데브옵스")) {
            String[] titles = {
                "Kubernetes 클러스터 구축 가이드",
                "Jenkins vs GitHub Actions 비교",
                "테라폼으로 인프라 관리하기",
                "프로메테우스 모니터링 설정법",
                "EKS vs GKE vs AKS 어떤 것이 좋을까요?"
            };
            return titles[index % titles.length];
        } else if (guildName.contains("알고리즘")) {
            String[] titles = {
                "DP 문제 풀이 전략",
                "그래프 알고리즘 정리",
                "이진 탐색 활용 팁",
                "코딩 테스트 준비 방법",
                "시간 복잡도 최적화 기법"
            };
            return titles[index % titles.length];
        } else {
            return guildName + " - 개발 주제 게시글 " + index;
        }
    }

    private String getRandomPostContent(String guildName, int index) {
        return "이 글은 " + guildName + "에 게시된 개발 주제 게시글입니다. " +
               "여기에는 코드 예시, 기술 정보, 경험담 등 다양한 개발 지식이 공유됩니다. " +
               "커뮤니티와 함께 성장하는 개발자 생활을 응원합니다! (게시글 인덱스: " + index + ")";
    }

    private String getRandomComment(String postTitle, int index) {
        String[] comments = {
            "정말 유익한 정보네요! 감사합니다.",
            "저도 비슷한 경험이 있는데 도움이 많이 됐어요.",
            "추가 자료나 예시 코드가 있으면 공유해주세요.",
            "이 부분에 대해 더 자세히 알고 싶습니다.",
            "좋은 내용 감사합니다. 바로 적용해보겠습니다!"
        };
        return comments[index % comments.length] + " (댓글 인덱스: " + index + ", 게시글: " + postTitle + ")";
    }
}
