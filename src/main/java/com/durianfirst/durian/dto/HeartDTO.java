package com.durianfirst.durian.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class HeartDTO {

    private Long hno;
    private int pno;
    private String mid;
    private LocalDateTime regDate, modDate;
}
