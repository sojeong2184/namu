package com.durianfirst.durian.repository;

import com.durianfirst.durian.entity.ItemImage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemImageRepository extends JpaRepository<ItemImage, Long> {

    /*List<ItemImage> findByItemInoOrderByIdAsc(Long ItemId);*/

    /*ItemImg findByPnoAndIimgrep(Long pno, String iimgrep); // 상품의 대표 이미지를 찾음*/

    /*List<ItemImage> findByItemIdOrderByIdAsc(Long ItemId);*/

    ItemImage findByInumAndIimgrep(Long inum, String iimgrep); // 상품의 대표 이미지를 찾음
}
