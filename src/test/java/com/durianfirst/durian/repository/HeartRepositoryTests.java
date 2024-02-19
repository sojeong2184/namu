package com.durianfirst.durian.repository;

import com.durianfirst.durian.entity.Heart;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.stream.IntStream;

@SpringBootTest
public class HeartRepositoryTests {

    @Autowired
    private HeartRepository heartRepository;

    @Test
    public void insertDum() {
        IntStream.rangeClosed(1,100).forEach(i -> {

            Heart heart = Heart.builder()
                    .pno(i*10)
                    .mid("회원계정 "+i+"번 닉네임")
                    .build();
            System.out.println(heartRepository.save(heart));
        });
    }
}
