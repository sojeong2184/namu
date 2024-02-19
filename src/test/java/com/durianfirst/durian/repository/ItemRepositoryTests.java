package com.durianfirst.durian.repository;

import com.durianfirst.durian.entity.Item;
import com.durianfirst.durian.entity.ItemImage;
import com.durianfirst.durian.entity.Member;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.UUID;
import java.util.stream.IntStream;

@SpringBootTest
public class ItemRepositoryTests {

    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    private ItemImageRepository itemImageRepository;

    @Test
    public void insertItems(){
        IntStream.range(1,10).forEach(i ->{
            Item item = Item.builder()
                    .iname("중고" + i)
                    .iprice(i*1000)
                    .isaleStatus("판매중")
                    .icategory("카테고리" + i)
                    .idealway("거래방법" + i)
                    .ilocation("거래위치" + i)
                    .idescription("설명" + i)
                    .icondition("물품"+i)
                    .member(Member.builder().mid("member"+i).build())
                    .build();

            System.out.println("==============================");

            itemRepository.save(item);

            int count = (int)(Math.random()*6)+1;

            for(int j=0; j<count; j++) {
                ItemImage itemImage = ItemImage.builder()
                        .uuid(UUID.randomUUID().toString())
                        .item(item)
                        .imgName("test"+j+".jpg").build();

                itemImageRepository.save(itemImage);
            }

            System.out.println("----------------------------------------");
        });
    }
}
