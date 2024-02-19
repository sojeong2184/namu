package com.durianfirst.durian.service;

import com.durianfirst.durian.dto.EventDTO;
import com.durianfirst.durian.dto.PageRequestDTO;
import com.durianfirst.durian.dto.PageResultDTO;
import com.durianfirst.durian.entity.Event;
import com.durianfirst.durian.entity.EventImage;
import com.durianfirst.durian.repository.EventImageRepository;
import com.durianfirst.durian.repository.EventRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.function.Function;

@Service
@Log4j2
@RequiredArgsConstructor
public class EventServiceImpl implements EventService {

    private final EventRepository eventRepository;

    private final EventImageRepository eventImageRepository;

    @Transactional
    @Override
    public Long register(EventDTO eventDTO) {

        Map<String, Object> entityMap = dtoToEntity(eventDTO);
        Event event = (Event) entityMap.get("event");
        List<EventImage> eventImageList = (List<EventImage>) entityMap.get("imgList");

        eventRepository.save(event);

        eventImageList.forEach(eventImage -> {
            eventImageRepository.save(eventImage);
        });

        return event.getEno();
    }

    @Override
    public void modify(EventDTO eventDTO) {
        //업데이트 하는 항목은 제목, 내용만

        Optional<Event> result = eventRepository.findById(eventDTO.getEno());

        if(result.isPresent()) {

            Event entity=result.get();

            entity.changeTitle(eventDTO.getEtitle());
            entity.changeContent(eventDTO.getEcontent());

            eventRepository.save(entity);
        }
    }

    @Override
    public void remove(Long eno) {
        eventRepository.deleteById(eno);
    }


    @Override
    public PageResultDTO<EventDTO, Object[]> getList(PageRequestDTO requestDTO) {

        Pageable pageable = requestDTO.getPageable(Sort.by("eno").descending());

        Page<Object[]> result = eventRepository.getListPage(pageable);

        log.info("==============================================");
        result.getContent().forEach(arr -> {
            log.info(Arrays.toString(arr));
        });


        Function<Object[], EventDTO> fn = (arr -> entitiesToDTO(
                (Event)arr[0] ,
                (List<EventImage>)(Arrays.asList((EventImage)arr[1]))

        ));

        return new PageResultDTO<>(result, fn);
    }

    @Override
    public EventDTO getEvent(Long eno) {

        List<Object[]> result = eventRepository.getEventWithAll(eno);

        Event event = (Event) result.get(0)[0];

        List<EventImage> eventImageList = new ArrayList<>();

        result.forEach(arr -> {
            EventImage  eventImage = (EventImage)arr[1];
            eventImageList.add(eventImage);
        });


        return entitiesToDTO(event, eventImageList);
    }


}
    

