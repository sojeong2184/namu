package com.durianfirst.durian.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
public class SellItem extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long sino;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ino")
    private Item item;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sno")
    private Sell sell;

    @Column(nullable = false, length = 20)
    private int siprice; //아이템 단가

    @Column(nullable = false, length = 5)
    private int sicount; //구매 수량


}
