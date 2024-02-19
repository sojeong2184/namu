package com.durianfirst.durian.repository;

import com.durianfirst.durian.entity.Item;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ItemRepository extends JpaRepository<Item, Long> {

    @Query("select i, ii from Item i " +
            " left outer join ItemImage ii on ii.item = i group by i")
    Page<Object[]> getListPage(Pageable pageable); //페이지처리

    @Query("select i, ii " +
            " from Item i left outer join ItemImage ii on ii.item = i " +
            " where i.ino = :ino group by ii")
    List<Object[]> getItemWithAll(Long ino); //특정 제품 조회

    @Query("select i, ii from Item i " +
            " left outer join ItemImage ii on ii.item = i" +
            " where i.member.mid = :mid group by ii")
    List<Object[]> getMidWithAll(String mid); //특정 제품 조회

    @Query("SELECT COUNT(i) FROM Item i")
    Long countItem(); //총 중고물품수

    @Query("select i,ii from Item i " +
            " left outer join ItemImage ii on ii.item = i " +
            " where i.iname like %:iname% ")
    List<Object[]> getInameWithAll(String iname);

    List<Item> findByMember_Mid(String mid); //자신의 물품검색

}
