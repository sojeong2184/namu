package com.durianfirst.durian.repository;

import com.durianfirst.durian.entity.Answer;
import com.durianfirst.durian.entity.Question;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@Log4j2
public class QnARepositoryTests {

    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private AnswerRepository answerRepository;


    @Test
    public void testInsert() { //질문생성
        IntStream.rangeClosed(1, 50).forEach(i -> {
            Question question = Question.builder()
                    .qtitle("질문제목" + i)
                    .qcate("카테고리" + i)
                    .qcontent("질문내용" + i)
                    .password("1111")
                    .build();

            Question result = questionRepository.save(question);
            log.info("QNO: " + result.getQno());
        });
    }

    @Test
    void testInsert2() { //답변생성
        Optional<Question> oq = this.questionRepository.findById(6L);
        assertTrue(oq.isPresent());
        Question q = oq.get();

        Answer a = new Answer();
        a.setAcontent("질문에 대한 답변Test.");
        a.setAquestion(q);  // 어떤 질문의 답변인지 알기위해서 Question 객체가 필요하다.
        a.setRegDate(LocalDateTime.now());
        this.answerRepository.save(a);
    }

    @Test
    public void testSelect() {
        Long qno = 30L;

        Optional<Question> result = questionRepository.findById(qno);

        Question question = result.orElseThrow();

        //  log.info(question); 이걸 빼야지 됌.
    }

    @Test
    public void testPaging() {

        Pageable pageable = PageRequest.of(0, 10, Sort.by("qno").descending());

        Page<Question> result = questionRepository.findAll(pageable);
    }

    @Test
    public void testSearch1() {

        Pageable pageable = PageRequest.of(1, 10, Sort.by("qno").descending());

        questionRepository.search1(pageable);
    }

    @Test
    public void testSearchAll() {

        String[] types = {"t", "c"};

        String keyword = "1";

        Pageable pageable = PageRequest.of(0, 10, Sort.by("qno").descending());

        Page<Question> result = questionRepository.searchAll(types, keyword, pageable);
    }

    @Test
    @Transactional //이거하니까 됌.
    public void testSearchAll2() { //페이지관련정보 추출 //안됨..

        String[] types = {"t", "c"};

        String keyword = "1";

        Pageable pageable = PageRequest.of(0, 10, Sort.by("qno").descending());

        Page<Question> result = questionRepository.searchAll(types, keyword, pageable);

        //total pages
        log.info(result.getTotalPages());

        //page size
        log.info(result.getSize());

        //pageNumber
        log.info(result.getNumber());

        //prev next
        log.info(result.hasPrevious() + ": " + result.hasNext());

        result.getContent().forEach(question -> log.info(question));

    }
    /*--------------------*/
    //findBy + 엔티티의 속성명으로 조회
    @Test
    void testJpa(){
        Question q = this.questionRepository.findByQtitleAndQcontent(
                "수정제목수정", "내용수정내용수정");
        assertEquals(2, q.getQno());
    }
    }
    
