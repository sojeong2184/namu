package com.durianfirst.durian.service;

import com.durianfirst.durian.dto.PageRequestedDTO;
import com.durianfirst.durian.dto.PageResponsedDTO;
import com.durianfirst.durian.dto.QuestionDTO;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@Log4j2
public class QuestionServiceTests {

    @Autowired
    private QuestionService questionService;

    @Test
    public void testRegister(){

        log.info(questionService.getClass().getName());
    }
    @Test
    public void testModify(){

        //변경이 필요한 데이터만
        QuestionDTO questionDTO = QuestionDTO.builder()
                .qno(2L)
                .qtitle("수정제목수정")
                .qcontent("내용수정내용수정")
                .build();

        questionService.modify(questionDTO);
    }
    @Test
    public void testList(){

        PageRequestedDTO pageRequestDTO = PageRequestedDTO.builder()
                .type("tc")
                .keyword("1")
                .page(1)
                .size(10)
                .build();

        PageResponsedDTO<QuestionDTO> responseDTO = questionService.list(pageRequestDTO);

        log.info(responseDTO);
    }
}
