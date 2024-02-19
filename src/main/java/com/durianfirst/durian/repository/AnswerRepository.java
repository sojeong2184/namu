package com.durianfirst.durian.repository;

import com.durianfirst.durian.entity.Answer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AnswerRepository extends JpaRepository<Answer,Long> {//<Answer,pk타입>
    Page<Answer> findAll(Pageable pageable);

    Answer findByAcontent(String acontent);//제목으로 테이블조회
    List<Answer> findByAcontentLike(String acontent);//제목에 특정문자열포함 데이터조회
}
