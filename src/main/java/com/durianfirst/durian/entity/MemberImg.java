package com.durianfirst.durian.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name="memberimage")
@Getter @Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString(exclude = "member")
public class MemberImg extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long mimgno;


    private String mimgurl; //이미지 조회 경로

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "mid")
    private Member member;

    public void updateImg(String mimgurl){
        // 이미지 경로를 파라미터로 입력받아 이미지 정보를 업데이트
        this.mimgurl = mimgurl;
    }

}