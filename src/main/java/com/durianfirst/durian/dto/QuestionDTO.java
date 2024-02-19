package com.durianfirst.durian.dto;

import com.durianfirst.durian.entity.Answer;
import com.durianfirst.durian.entity.Member;
import lombok.*;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.persistence.Column;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class QuestionDTO {

    private Long qno; //질문 번호(pk)

    @NotEmpty(message = "제목은 필수항목입니다.") /*Null허용치않음*/
    @Column(nullable = false)
    private String qtitle; //질문제목

    @NotEmpty(message = "질문내용은 필수항목입니다.")
    @Column(nullable = false, length = 5000)
    private String qcontent; //질문내용

    @NotBlank(message = "카테고리선택은 필수항목입니다.")
    @Column(nullable = false)
    private String qcate; //카테고리

    private Member member; //작성자

    private String qmember;

    private LocalDateTime regDate;

    private LocalDateTime modDate;

    private List<Answer> answerList;

    private Integer view; //조회수

    private String password; //비밀글 비밀번호

    private Boolean secret; //비밀글 유무


    public Boolean getSecret(){
        return secret != null && secret; //secret 체크를하지 않았을 경우 false/ 이외 true
    }

    /* 비밀번호 검증 메소드 추가 */
    public boolean isPasswordValid(String enteredPassword, PasswordEncoder passwordEncoder){
        return passwordEncoder.matches(enteredPassword, this.password);
    }
}

