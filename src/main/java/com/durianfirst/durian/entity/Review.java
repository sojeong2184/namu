package com.durianfirst.durian.entity;

import lombok.*;

import javax.persistence.*;

@Table(name="review")
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString(exclude = {"item","member", "orders"})
public class Review extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long rno;

    @Column(nullable = false, length = 1000)
    private String rcontent;    // 리뷰내용

/*    @Column(nullable = false)
    @Min(value = 1, message = "최소 평점은 1부터 시작합니다.")
    @Max(value = 5, message = "")
    private int rscore;      // 평점*/

    // 연관관계
    @ManyToOne(fetch = FetchType.LAZY)
    private Item item;
    @ManyToOne(fetch = FetchType.LAZY)
    private Member member;
    @OneToOne(fetch = FetchType.LAZY)
    private Order order;

}
