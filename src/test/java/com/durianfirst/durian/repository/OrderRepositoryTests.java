package com.durianfirst.durian.repository;

import com.durianfirst.durian.entity.Item;
import com.durianfirst.durian.entity.Member;
import com.durianfirst.durian.entity.Order;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;
import org.springframework.transaction.annotation.Transactional;

import java.util.stream.IntStream;

@SpringBootTest
@Log4j2
public class OrderRepositoryTests {

    @Autowired
    private OrderRepository orderRepository;

    @Commit
    @Transactional
    @Test
    public void testInsert() {
        IntStream.rangeClosed(1, 10).forEach(i -> {
            Order order=Order.builder()
                    .member(Member.builder().mid("member"+i).build())
                    .item(Item.builder().ino((long) i).build())
                    .totalPrice(i*1000)
                    .build();
            Order result=orderRepository.save(order);
            log.info("ONO: " + result.getOno());
        });
    }

}
