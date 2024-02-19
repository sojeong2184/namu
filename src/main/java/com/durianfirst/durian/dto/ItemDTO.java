package com.durianfirst.durian.dto;

import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ItemDTO {

    private Long ino; //중고물품번호

    private String iname; //중고제목

    private int iprice; //가격

    private String isaleStatus; //제품상태

    private String icategory; //카테고리

    private String idealway; //거래방식

    private String ilocation; //주소

    private String idescription; //상세설명

    private String icondition; //제품상태

    private String mid; //회원 아이디

    private LocalDateTime regDate; //등록일

    private LocalDateTime modDate; //수정일

    @Builder.Default
    private List<ItemImageDTO> imageDTOList = new ArrayList<>(); //제품이미지



}
