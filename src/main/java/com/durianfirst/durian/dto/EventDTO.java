package com.durianfirst.durian.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EventDTO {

    private Long eno;

    @NotBlank(message = "제목은 필수 입력 값입니다.")
    private String etitle;

    @NotBlank(message = "내용은 필수 입력 값입니다.")
    private String econtent;
    private LocalDateTime regDate, modDate;

    @Builder.Default
    private List<EventImageDTO> imageDTOList = new ArrayList<>(); //제품이미지



}
