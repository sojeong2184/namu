package com.durianfirst.durian.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name="answer")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Answer extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long ano; //답변 번호(pk)

    @Column(nullable = false, length = 5000)
    private String acontent; //답변내용

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "mid")
    private Member member; //작성자(작성자 한 사람이 답변 1개만 달 수 있음)

    @ManyToOne(fetch = FetchType.LAZY)
    private Question aquestion; //질문(질문 하나당 답변 1개)

    public void change(String qcontent) { //수정 가능한것
        this.acontent = acontent;

    }
}
