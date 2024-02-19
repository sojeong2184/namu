package com.durianfirst.durian.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@ToString(exclude = "item") //연관관계주의하길
public class ItemImage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long inum;

    private String uuid;

    private String imgName;

    private String path;

    private String iimgrep; //대표 이미지 여부

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    @JoinColumn(name = "item_ino")
    private Item item;

    public void updateItemImg(String uuid, String imgName, String path) {
        // 원본 이미지 파일명, 업데이트할 이미지 파일명, 이미지 경로를 파라미터로 입력받아 이미지 정보를 업데이트
        this.uuid = uuid;
        this.imgName = imgName;
        this.path = path;

    }
}
