package com.durianfirst.durian.entity;

import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString(exclude = {"member"})
public class Item extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long ino; //물품번호

    @ManyToOne(fetch = FetchType.LAZY)
    private Member member;

    private String iname; //물품이름

    private int iprice;//물품가격

    private String isaleStatus; //판매상태

    private String icategory;//카테고리

    private String idealway; //거래방법

    private String ilocation;//거래위치

    private String idescription; //물품설명

    private String icondition; //물품상태

    @OneToMany(mappedBy = "item", cascade = CascadeType.REMOVE)
    private List<ItemImage> images;

    public void changeIname(String iname){
        this.iname = iname;
    }

    public void changeIprice(int iprice){
        this.iprice = iprice;
    }

    public void changeIsaleStatus(String isaleStatus) { this.isaleStatus = isaleStatus;}

    public void changeIcategory(String icategory){
        this.icategory = icategory;
    }

    public void changeIdealway(String idealway){
        this.idealway = idealway;
    }

    public void changeIlocation(String ilocation){
        this.ilocation = ilocation;
    }

    public void changeIdescription(String idescription){
        this.idescription = idescription;
    }

    public void changeIcondition(String icondition){
        this.icondition = icondition;
    }

}
