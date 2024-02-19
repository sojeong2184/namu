package com.durianfirst.durian.dto;


import lombok.*;

import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;

@Data
@Builder
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class NoticeDTO {

    private Long nno;

    @NotBlank(message = "제목은 필수 입력 값입니다.")
    private String ntitle;

    @NotBlank(message = "내용은 필수 입력 값입니다.")
    private String ncontent;

    private LocalDateTime regDate, modDate;

    private Integer view; //조회수


}
