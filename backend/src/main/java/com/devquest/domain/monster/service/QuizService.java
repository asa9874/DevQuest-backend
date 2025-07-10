package com.devquest.domain.monster.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.devquest.domain.auth.util.AuthUtil;
import com.devquest.domain.member.model.Member;
import com.devquest.domain.member.repository.MemberRepository;
import com.devquest.domain.monster.dto.requestDto.QuizCreateRequestDto;
import com.devquest.domain.monster.dto.requestDto.QuizUpdateRequestDto;
import com.devquest.domain.monster.dto.responseDto.QuizResponseDto;
import com.devquest.domain.monster.dto.responseDto.QuizWithOutAnswerResponseDto;
import com.devquest.domain.monster.model.Quiz;
import com.devquest.domain.monster.repository.QuizRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class QuizService {
    private final MemberRepository memberRepository;
    private final QuizRepository quizRepository;

    public void createQuiz(
            QuizCreateRequestDto requestDto,
            Long memberId) {

        if (!AuthUtil.isAdminOrEqualMember(memberId)) {
            throw new IllegalArgumentException("권한이 없습니다.");
        }

        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 회원입니다."));

        Quiz quiz = Quiz.builder()
                .title(requestDto.title())
                .question(requestDto.question())
                .option1(requestDto.option1())
                .option2(requestDto.option2())
                .option3(requestDto.option3())
                .option4(requestDto.option4())
                .answer(requestDto.answer())
                .creater(member)
                .build();
        quizRepository.save(quiz);
    }

    public List<QuizResponseDto> getAllQuizzes() {
        List<Quiz> quizzes = quizRepository.findAll();
        return quizzes.stream()
                .map(QuizResponseDto::from)
                .toList();
    }

    public QuizResponseDto getQuizById(Long quizId) {
        Quiz quiz = quizRepository.findById(quizId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 퀴즈입니다."));

        if (!AuthUtil.isAdminOrEqualMember(quiz.getCreater().getId())) {
            throw new IllegalArgumentException("권한이 없습니다.");
        }
        return QuizResponseDto.from(quiz);
    }

    public QuizWithOutAnswerResponseDto getQuizWithoutAnswer(Long quizId) {
        Quiz quiz = quizRepository.findById(quizId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 퀴즈입니다."));
        return QuizWithOutAnswerResponseDto.from(quiz);
    }

    public void updateQuiz(
            Long quizId,
            QuizUpdateRequestDto requestDto,
            Long memberId) {

        if (!AuthUtil.isAdminOrEqualMember(memberId)) {
            throw new IllegalArgumentException("권한이 없습니다.");
        }

        Quiz quiz = quizRepository.findById(quizId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 퀴즈입니다."));

        if (quiz.getCreater().getId() != memberId && !AuthUtil.isAdmin()) {
            throw new IllegalArgumentException("퀴즈 작성자만 수정할 수 있습니다.");
        }

        quiz.update(
                requestDto.title(),
                requestDto.question(),
                requestDto.option1(),
                requestDto.option2(),
                requestDto.option3(),
                requestDto.option4(),
                requestDto.answer());
        quizRepository.save(quiz);
    }

    public void deleteQuiz(Long quizId, Long memberId) {
        if (!AuthUtil.isAdminOrEqualMember(memberId)) {
            throw new IllegalArgumentException("권한이 없습니다.");
        }

        Quiz quiz = quizRepository.findById(quizId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 퀴즈입니다."));

        if (quiz.getCreater().getId() != memberId && !AuthUtil.isAdmin()) {
            throw new IllegalArgumentException("퀴즈 작성자만 삭제할 수 있습니다.");
        }

        quizRepository.delete(quiz);
    }

}
