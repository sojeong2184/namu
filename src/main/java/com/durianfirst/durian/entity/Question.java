package com.durianfirst.durian.entity;

import lombok.*;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.util.List;

@Entity
@Table(name="question")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Question extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) //데이터베이스에 위임
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

    //여러개의질문이 한명의 사용자에게 작성될수 있으므로 ManyToOne
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "mid")
    private Member member;

    @OneToMany(mappedBy = "aquestion", cascade = CascadeType.REMOVE , fetch = FetchType.LAZY)
    private List<Answer> answerList;

    private int view; //조회수

    private String password; //비밀글 비밀번호

    private boolean secret; //비밀글 유무 체크

    public void change(String qtitle, String qcontent){ //수정가능한것 제목/내용
        this.qtitle = qtitle;
        this.qcontent = qcontent;
    }

    public void encryptPassword(PasswordEncoder passwordEncoder){ //암호화 로직 추가
        if(this.password != null){
            this.password = passwordEncoder.encode(this.password);
        }

    }

}
/*@NotNull : Null 값 체크

@NotEmpty : Null, "" 체크

@NotBlank : Null, "", 공백을 포함한 빈값 체크*/

