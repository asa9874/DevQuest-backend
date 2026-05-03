# 🎮 DevQuest
게임처럼 즐기며 학습할 수 있는 개발자 플랫폼

개발자가 퀘스트, 스킬, 몬스터, 길드 시스템을 통해 게임을 하듯 재미를 느끼며 역량을 키울 수 있도록 기획된 프로젝트입니다.

## 🔗 Links
- **DB Diagram**: [dbdiagram.io 링크](https://dbdiagram.io/d/DevQuest-67e959734f7afba184b88022)

## 🎯 프로젝트 목표
- 단순한 게시글 기능을 넘어, **여러 엔티티 간의 복잡한 관계**가 얽힌 시스템을 설계하고 구현하는 경험 축적
- **JPA와 JPQL**을 활용해 엔티티 간의 관계를 효율적으로 처리하는 구조 설계
- 비즈니스 중심의 설계를 위한 **도메인 중심 디렉토리 구조** 채택

## 🛠 Tech Stack
- **Backend**: Spring Boot, Spring Data JPA
- **AI Server**: Flask, LangChain
- **Database**: MySQL, Redis
- **Auth & Network**: JWT, OAuth2, STOMP (WebSocket + SockJS)
- **Infrastructure**: AWS EC2 (Ubuntu), Docker, NGINX
- **Frontend**: React, Vite, TypeScript, Tailwind CSS

![Flask](https://img.shields.io/badge/flask-%23000.svg?style=for-the-badge&logo=flask&logoColor=white)
![Docker](https://img.shields.io/badge/docker-%230db7ed.svg?style=for-the-badge&logo=docker&logoColor=white)

![Spring](https://img.shields.io/badge/spring-%236DB33F.svg?style=for-the-badge&logo=spring&logoColor=white)
![MySQL](https://img.shields.io/badge/mysql-4479A1.svg?style=for-the-badge&logo=mysql&logoColor=white)
![Redis](https://img.shields.io/badge/redis-%23DD0031.svg?style=for-the-badge&logo=redis&logoColor=white)

![React](https://img.shields.io/badge/react-%2320232a.svg?style=for-the-badge&logo=react&logoColor=%2361DAFB)
![Vite](https://img.shields.io/badge/vite-%23646CFF.svg?style=for-the-badge&logo=vite&logoColor=white)
![TypeScript](https://img.shields.io/badge/typescript-%23007ACC.svg?style=for-the-badge&logo=typescript&logoColor=white)

<img width="601" height="411" alt="image" src="https://github.com/user-attachments/assets/d8e5d66f-e20d-4ed6-8779-a3cf3327f7a5" />

## 💡 주요 기능

### 1. 회원 및 인증 (Auth/Member)
- Spring Security와 JWT를 활용한 Stateless 인증 구현
- JWT Payload에 이메일, 회원 ID, 권한(Role) 정보 포함
- Redis를 활용하여 로그아웃 시 토큰을 블랙리스트에 등록하여 만료 처리

### 2. 퀘스트 (Quest)
- 사용자가 자신만의 퀘스트를 생성하고 타인과 공유하며 학습
- 자기계발 목적이므로 퀘스트의 성공/실패 여부는 도전한 사용자 본인이 직접 결정

### 3. 길드 (Guild)
- 사용자들 간 정보를 공유하고 소통할 수 있는 단체 시스템
- 길드장(OWNER), 관리자(ADMIN), 일반 회원(MEMBER) 권한 분리 및 가입/탈퇴/강제 추방 지원
- 길드 내 게시글 및 댓글 작성 기능
- **STOMP 기반 WebSocket**을 통한 길드 전용 실시간 채팅 제공

### 4. 몬스터 및 퀴즈 (Monster & Quiz)
- 몬스터는 여러 퀴즈들의 집합으로 구성
- 몬스터 도전을 통해 연관된 퀴즈를 풀고, 정답 개수에 따라 사냥 성공 여부 결정

### 5. AI 연동
- Flask 및 LangChain을 활용한 AI 서버 구축
- LLM을 통해 퀴즈 및 퀘스트 자연어 자동 생성
- AI를 활용한 길드 검색 기능 제공

### 6. 스킬 (Skill)
- 사용자가 습득할 수 있는 기술 스택 시스템
- 선제 스킬 습득, 퀘스트 완료, 몬스터 처치 등 지정된 사전 조건을 만족해야 스킬 획득 가능

## 🚀 배포 및 트러블슈팅

### 인프라 구조
- AWS EC2 프리티어(t2.micro) 환경에서 Docker Compose를 사용하여 배포
- 실제 포트를 노출하지 않고 보안을 강화하기 위해 NGINX 리버스 프록시 적용

### 트러블슈팅 및 회고
1. **단방향 매핑과 DTO Projection**
   - 엔티티 방향을 단방향으로 설정하고, 응답 반환 시 **JPQL DTO Projection**을 사용하여 한 번의 쿼리로 데이터를 효율적으로 조회했습니다.
2. **Cascade 처리 문제**
   - 모든 엔티티를 단방향으로 구성하여 중간 테이블 자동 삭제(Cascade)가 불가능했습니다. 이를 해결하기 위해 게시글 삭제 시 댓글도 함께 삭제되도록 Service 계층에서 로직을 직접 제어하며 복잡한 의존성 문제를 해결했습니다.
3. **배포 중 메모리 부족 현상**
   - EC2 프리티어(1GB RAM) 환경에서 Spring Boot, Flask, DB 컨테이너 동시 실행 시 서버 다운 발생.
   - **해결**: 2GB의 Swap 메모리를 추가하고, MySQL과 Redis의 컨테이너 메모리 사용량을 제한하여 안정적인 배포에 성공했습니다.
4. **Redis 활용 계획 변경**
   - 초기 기획에서는 일일 퀘스트 및 실시간 인기 퀘스트 캐싱에 사용할 예정이었으나, 기획 변경으로 제외되어 현재는 **로그아웃 토큰 블랙리스트 관리용**으로만 사용하고 있습니다.

