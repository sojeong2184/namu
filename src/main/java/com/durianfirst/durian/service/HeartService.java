package com.durianfirst.durian.service;

import com.durianfirst.durian.dto.HeartDTO;
import com.durianfirst.durian.entity.Heart;

public interface HeartService {

    Long register(HeartDTO heartDTO);

    HeartDTO read(Long hno);

    void remove(Long hno);

//    void modify(HeartDTO dto);

    default Heart dtoToEntity(HeartDTO heartDTO) {
        Heart entity = Heart.builder()
                .hno(heartDTO.getHno())
                .pno(heartDTO.getPno())
                .mid(heartDTO.getMid())
                .build();
        return entity;
    }

    default HeartDTO entityToDto(Heart entity) {

        HeartDTO heartDTO = HeartDTO.builder()
                .hno(entity.getHno())
                .pno(entity.getPno())
                .mid(entity.getMid())
                .regDate(entity.getRegDate())
                .modDate(entity.getModDate())
                .build();

        return heartDTO;
    }
}
