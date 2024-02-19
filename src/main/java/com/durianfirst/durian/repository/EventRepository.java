package com.durianfirst.durian.repository;

import com.durianfirst.durian.entity.Event;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

import java.util.List;

public interface EventRepository extends JpaRepository<Event, Long>, QuerydslPredicateExecutor<Event> {
    @Query("select e, ei    from Event e " +
            " left outer join EventImage ei on ei.event = e group by e ")
    Page<Object[]> getListPage(Pageable pageable); //페이지처리

    @Query("select e, ei  from Event e left outer join EventImage ei on ei.event = e " +
            " where e.eno = :eno group by ei")
    List<Object[]> getEventWithAll(Long eno); //특정 제품 조회


}
