package com.durianfirst.durian.repository;


import com.durianfirst.durian.entity.Event;
import com.durianfirst.durian.entity.EventImage;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;
import java.util.stream.IntStream;

@SpringBootTest
@Log4j2
public class EventRepositoryTests {

    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private EventImageRepository eventImageRepository;

    @Commit
    @Transactional
    @Test
    public void testInsert() {
        IntStream.rangeClosed(1, 100).forEach(i -> {
            Event event = Event.builder()
                    .etitle("etitle..." + i)
                    .econtent("econtent..." + i)
                    .build();

            Event result = eventRepository.save(event);
            log.info("ENO: " + result.getEno());
            int count = (int)(Math.random()*6)+1;

            for(int j=0; j<count; j++) {
                EventImage eventImage =EventImage.builder()
                        .uuid(UUID.randomUUID().toString())
                        .event(event)
                        .imgName("test"+j+".jpg").build();

                eventImageRepository.save(eventImage);
            }

        });
    }
}