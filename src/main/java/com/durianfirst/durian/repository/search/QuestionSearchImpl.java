package com.durianfirst.durian.repository.search;

import com.durianfirst.durian.entity.QQuestion;
import com.durianfirst.durian.entity.Question;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.JPQLQuery;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import java.util.List;

public class QuestionSearchImpl extends QuerydslRepositorySupport implements
QuestionSearch {

    public QuestionSearchImpl(){
        super(Question.class);
    }

    @Override //Q도메인을 이용하는 코드,where,group by 혹은 조인처리등 가능
    public Page<Question> search1(Pageable pageable){

        QQuestion question = QQuestion.question; //Q도메인객체

        JPQLQuery<Question> query = from(question);

        query.where(question.qtitle.contains("1"));

        //paging
        this.getQuerydsl().applyPagination(pageable, query);

        List<Question> list = query.fetch();

        long count = query.fetchCount();//count쿼리 실행

        return null;
    }
    @Override
    public Page<Question> searchAll(String[] types, String keyword, Pageable pageable){

        QQuestion question = QQuestion.question;
        JPQLQuery<Question> query = from(question);

        if ((types != null && types.length > 0) && keyword != null){//검색조건과 키워드가 있다면

            BooleanBuilder booleanBuilder = new BooleanBuilder(); //(

            for(String type: types) {

                switch (type) {
                    case "t":
                        booleanBuilder.or(question.qtitle.contains(keyword));
                        break;
                    case "c":
                        booleanBuilder.or(question.qcontent.contains(keyword));
                        break;
                }
            }//end for
            query.where(booleanBuilder);
            }//end if
        //qno>0
        query.where(question.qno.gt(0L));

        //Paging
        this.getQuerydsl().applyPagination(pageable,query);

        List<Question> list = query.fetch();

        long count = query.fetchCount();

        return new PageImpl<>(list, pageable, count);
    }
}
