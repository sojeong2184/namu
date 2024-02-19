package com.durianfirst.durian.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "sell")
@Getter
@Setter
public class Sell extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long sno; //번호

//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "mno")
//    private Member member;

    private LocalDateTime sdate; //주문일

    @OneToMany(mappedBy = "sell", cascade = CascadeType.ALL
            , orphanRemoval = true, fetch = FetchType.LAZY)
    private List<SellItem> sellItems;

}
