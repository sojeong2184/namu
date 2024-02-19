package com.durianfirst.durian.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Getter
@Setter
public class QuestionForm {
    @NotEmpty(message="제목은 필수항목입니다.")
    @Size(max=200)
    private String qtitle;

    @NotEmpty(message="내용은 필수항목입니다.")
    private String qcontent;

    @NotBlank(message = "카테고리선택은 필수항목입니다.")
    @Column(nullable = false)
    private String qcate; //카테고리

}
