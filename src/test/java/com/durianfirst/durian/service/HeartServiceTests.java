package com.durianfirst.durian.service;

import com.durianfirst.durian.dto.HeartDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.time.Month;

@SpringBootTest
public class HeartServiceTests {

    @Autowired
    private HeartService heartService;

    @Test
    public void testRegister() {

        HeartDTO heartDTO = HeartDTO.builder()
                .pno(33)
                .mid("고은짜이")
                .regDate(LocalDateTime.of(2023, Month.OCTOBER, 21, 14, 30))
                .build();

        System.out.println(heartService.register(heartDTO));
    }
}
