package com.durianfirst.durian.dto;

import com.durianfirst.durian.entity.Question;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class QuestionResponseDTO {

    private final Long qno;
    private final String qtitle;
    private final String qcontent;
    private final String qcate;
    private final String member;
    private final String password;

    private final boolean secret; //비밀글 유무

    public static QuestionResponseDTO from(Question question){
        return QuestionResponseDTO.builder()
                .qno(question.getQno())
                .qtitle(question.getQtitle())
                .qcontent(question.getQcontent())
                .qcate(question.getQcate())
                .secret(question.getPassword() != null)
                .build();
    }

}
