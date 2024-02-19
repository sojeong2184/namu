package com.durianfirst.durian.dto;

import com.durianfirst.durian.entity.Member;
import com.durianfirst.durian.entity.Question;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AnswerDTO {

    private Long ano; //답변 번호(pk)

    private String acontent; //답변내용

    private Member member; //작성자(작성자 한 사람이 답변 1개만 달 수 있음)

    private Question aquestion; //질문(질문 하나당 답변 1개)

    private LocalDateTime regTime;

    private LocalDateTime updateTime;

    }
