package com.durianfirst.durian.repository;


import com.durianfirst.durian.entity.Notice;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;
import java.util.stream.IntStream;

@SpringBootTest
@Log4j2
public class NoticeRepositoryTests {

    @Autowired
    private NoticeRepository noticeRepository;

    @Test
    public void testInsert() {
        IntStream.rangeClosed(1, 100).forEach(i -> {
            Notice notice = Notice.builder()
                    .ntitle("ntitle..." + i)
                    .ncontent("ncontent..." + i)
                    .build();

            Notice result = noticeRepository.save(notice);
            log.info("NNO: " + result.getNno());
        });
    }


    @Test
    public void testSelect() {
        Long nid = 100L;

        Optional<Notice> result = noticeRepository.findById(nid);

        Notice notice = result.orElseThrow();

        log.info(notice);

    }



}