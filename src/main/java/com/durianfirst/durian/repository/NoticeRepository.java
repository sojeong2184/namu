package com.durianfirst.durian.repository;

import com.durianfirst.durian.entity.Notice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;


public interface NoticeRepository extends JpaRepository<Notice, Long>, QuerydslPredicateExecutor<Notice> {

    /* 조회수 */
    @Modifying
    @Query("update Notice n set n.view = n.view + 1 where n.nno = :nno")
    int updateView(Long nno);
}


