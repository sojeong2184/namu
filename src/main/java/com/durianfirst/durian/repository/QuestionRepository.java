package com.durianfirst.durian.repository;

import com.durianfirst.durian.entity.Question;
import com.durianfirst.durian.repository.search.QuestionSearch;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface QuestionRepository extends JpaRepository<Question,Long>, QuestionSearch {

    /* 조회수 */
    @Modifying
    @Query("update Question q set q.view = q.view + 1 where q.qno = :qno")
    int updateView(Long qno);

    Page<Question> findAll(Pageable pageable);

    Question findByQtitle(String qtitle);//제목으로 테이블조회
    Question findByQtitleAndQcontent(String qtitle, String qcontent);
    List<Question> findByQtitleLike(String qtitle);//제목에 특정문자열포함 데이터조회

    @Query(value = "select now()", nativeQuery = true)
    String getTime();

    @Query("SELECT COUNT(q) FROM Question q")
    Long countQuestion(); //총 결제수

    List<Question> findByMember_Mid(String mid); //아이디검색
}
