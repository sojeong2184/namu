package com.durianfirst.durian.service;

import com.durianfirst.durian.DataNotFoundException;
import com.durianfirst.durian.dto.QuestionDTO;
import com.durianfirst.durian.entity.Answer;
import com.durianfirst.durian.entity.Member;
import com.durianfirst.durian.entity.Question;
import com.durianfirst.durian.repository.AnswerRepository;
import com.durianfirst.durian.repository.QuestionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.Optional;

@Service
@Log4j2
@RequiredArgsConstructor
@Transactional
public class AnswerServiceImpl implements AnswerService {

    private final QuestionRepository questionRepository;

    private final AnswerRepository answerRepository;

    private final ModelMapper modelMapper;

    @Override
    public QuestionDTO create(Long qno) {

        Optional<Question> result = questionRepository.findById(qno);

        Question question = result.orElseThrow();

        QuestionDTO questionDTO = modelMapper.map(question, QuestionDTO.class);

        return questionDTO;
    }

    @Override
    public Answer createa(Question question, String acontent, Member member) {
        Answer answer = new Answer();
        answer.setAcontent(acontent);
        answer.setRegDate(LocalDateTime.now()); //변경
        answer.setAquestion(question);
        answer.setMember(member);

        this.answerRepository.save(answer);

        return answer;
    }


    @Override
    public Question getQuestion(Long qno) {
        Optional<Question> question = this.questionRepository.findById(qno);
        if (question.isPresent()) {
            return question.get();
        } else {
            throw new DataNotFoundException("question not found");
        }
    }

    @Override
    public Answer getAnswer(Long ano) {//답변의id로 답변을 조회
        Optional<Answer> answer = this.answerRepository.findById(ano);
        if (answer.isPresent()) {
            return answer.get();
        } else {
            throw new DataNotFoundException("answer not found");
        }
    }

    @Override//답변의 내용으로 답변을 수정
    public void modify(Answer answer, String acontent) {
        answer.setAcontent(acontent);
        answer.setModDate(LocalDateTime.now()); //변경
        this.answerRepository.save(answer);
    }

    @Override
    public void delete(Long ano) {

        answerRepository.deleteById(ano);
    }

    @Override
    public void delete(Answer answer) {
        this.answerRepository.delete(answer);
    }
}

