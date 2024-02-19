package com.durianfirst.durian.service;

import com.durianfirst.durian.dto.QuestionDTO;
import com.durianfirst.durian.entity.Answer;
import com.durianfirst.durian.entity.Member;
import com.durianfirst.durian.entity.Question;

public interface AnswerService {


    QuestionDTO create(Long qno);

    Question getQuestion(Long qno);

    Answer createa(Question question, String acontent, Member member);

    Answer getAnswer(Long ano);

    void modify(Answer answer, String acontent);

    void delete(Long ano);

    void delete(Answer answer);

    }
