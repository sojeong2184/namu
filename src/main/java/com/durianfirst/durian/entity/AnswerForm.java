package com.durianfirst.durian.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotEmpty;

@Getter
@Setter
public class AnswerForm {

    @NotEmpty(message = "내용은 필수항목입니다.")
    @Column(nullable = false, length = 5000)
    private String acontent;

    @OneToOne(fetch = FetchType.LAZY)
    private Member member; //작성자(작성자 한 사람이 답변 1개만 달 수 있음)

    @OneToOne(fetch = FetchType.LAZY)
    private Question aquestion; //질문(질문 하나당 답변 1개)
}

