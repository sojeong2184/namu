package com.durianfirst.durian.repository;

import com.durianfirst.durian.entity.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {

    @Query("select o from Order o ")
    Page<Object[]> getListPage(Pageable pageable); //주문 리스트 출력

    @Query("select o from Order o " +
            " where o.ono = :ono group by o"
    )
    List<Object[]> getOrderWithAll(Long ono); //해당 주문번호 조회

    @Query("select o, o.item.iname from Order o " +
            " where o.member.mid = :mid"
    )
    List<Object[]> getOrderMidWithAll(String mid); //해당 아이디로 주문한 주문리스트 조회

    @Query("select o from Order o " +
            " where o.item.ino = :ino group by o"
    )
    List<Object[]> getOrderInoWithAll(Long ino); //해당 주문번호 조회

    @Query("SELECT COUNT(o) FROM Order o")
    Long countOrder(); //총 결제수

    List<Order> findByMember_Mid(String mid); //아이디검색
}
