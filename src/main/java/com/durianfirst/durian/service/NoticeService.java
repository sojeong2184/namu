package com.durianfirst.durian.service;

import com.durianfirst.durian.dto.NoticeDTO;
import com.durianfirst.durian.dto.PageRequestDTO;
import com.durianfirst.durian.dto.PageResultDTO;
import com.durianfirst.durian.entity.Notice;

import java.time.LocalDateTime;

public interface NoticeService {
    Long register(NoticeDTO dto);

    NoticeDTO read(Long nno);

    void remove(Long nno);

    void modify(NoticeDTO dto);

    int updateView(Long nno);

    default Notice dtoToEntity(NoticeDTO dto) {
        Notice entity = Notice.builder()
                .nno(dto.getNno())
                .ntitle(dto.getNtitle())
                .ncontent(dto.getNcontent())
                .regDate(LocalDateTime.now())
                .view(dto.getView())
                .build();
        return entity;
    }
    default NoticeDTO entityToDto(Notice entity) {

        NoticeDTO dto = NoticeDTO.builder()
                .nno(entity.getNno())
                .ntitle(entity.getNtitle())
                .ncontent(entity.getNcontent())
                .regDate(entity.getRegDate())
                .modDate(entity.getModDate())
                .build();

        return dto;
    }

    PageResultDTO<NoticeDTO, Notice> getList(PageRequestDTO requestDTO);


}

