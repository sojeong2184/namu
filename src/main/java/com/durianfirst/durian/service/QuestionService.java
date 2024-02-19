package com.durianfirst.durian.service;

import com.durianfirst.durian.dto.PageRequestedDTO;
import com.durianfirst.durian.dto.PageResponsedDTO;
import com.durianfirst.durian.dto.QuestionDTO;
import com.durianfirst.durian.entity.Question;

import java.util.List;

public interface QuestionService {

    int updateView(Long qno);

    Long register(QuestionDTO questionDTO);

    QuestionDTO readOne(Long qno);


    void modify(QuestionDTO questionDTO);

    void remove(Long qno);

    PageResponsedDTO<QuestionDTO> list(PageRequestedDTO pageRequestDTO);


    public List<Question> getList();

    public boolean checkPassword(Long qno, String password);

    public boolean existsById(Long qno);

    Object count();

    List<Question> getQuestionsByMid(String mid);

}


