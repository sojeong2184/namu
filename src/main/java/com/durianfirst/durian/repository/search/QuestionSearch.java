package com.durianfirst.durian.repository.search;

import com.durianfirst.durian.entity.Question;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface QuestionSearch {//Querydsl을 기존코드에 연결하기위한

    Page<Question> search1(Pageable pageable);

    Page<Question> searchAll(String[] type,String keyword, Pageable pageable);
}
