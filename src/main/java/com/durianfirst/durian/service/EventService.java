package com.durianfirst.durian.service;

import com.durianfirst.durian.dto.EventDTO;
import com.durianfirst.durian.dto.EventImageDTO;
import com.durianfirst.durian.dto.PageRequestDTO;
import com.durianfirst.durian.dto.PageResultDTO;
import com.durianfirst.durian.entity.Event;
import com.durianfirst.durian.entity.EventImage;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public interface EventService {
    Long register(EventDTO eventDTO);

    void modify(EventDTO eventDTO);

    void remove(Long eno);

    PageResultDTO<EventDTO, Object[]> getList(PageRequestDTO requestDTO); //목록 처리

    EventDTO getEvent(Long eno);


    default EventDTO entitiesToDTO(Event event, List<EventImage> eventImages){
        EventDTO eventDTO = EventDTO.builder()
                .eno(event.getEno())
                .etitle(event.getEtitle())
                .econtent(event.getEcontent())
                .regDate(event.getRegDate())
                .modDate(event.getModDate())
                .build();

        List<EventImageDTO> eventImageDTOList = eventImages.stream().map(eventImage -> {
            return EventImageDTO.builder().imgName(eventImage.getImgName())
                    .path(eventImage.getPath())
                    .uuid(eventImage.getUuid())
                    .build();
        }).collect(Collectors.toList());

        eventDTO.setImageDTOList(eventImageDTOList);
       



        return eventDTO;

    }

    default Map<String, Object> dtoToEntity(EventDTO eventDTO) {
        Map<String, Object> entityMap = new HashMap<>();

        Event event = Event.builder()
                .eno(eventDTO.getEno())
                .etitle(eventDTO.getEtitle())
                .econtent(eventDTO.getEcontent())
                .regDate(LocalDateTime.now())
                .build();
        entityMap.put("event",event);

        List<EventImageDTO> imageDTOSList = eventDTO.getImageDTOList();

        //eventImageDTO처리
        if(imageDTOSList != null && imageDTOSList.size() > 0) {
            List<EventImage> eventImageList = imageDTOSList.stream().map(eventImageDTO -> {
                EventImage eventImage = EventImage.builder()
                        .path(eventImageDTO.getPath())
                        .imgName(eventImageDTO.getImgName())
                        .uuid(eventImageDTO.getUuid())
                        .event(event)
                        .build();
                return eventImage;
            }).collect(Collectors.toList());
            entityMap.put("imgList", eventImageList);
        }
        return entityMap;
    }


}


