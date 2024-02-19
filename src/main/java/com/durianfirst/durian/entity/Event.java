package com.durianfirst.durian.entity;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;
@Entity
@Table(name="event")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Event extends BaseEntity {

    @Id
    @Column(name="eno")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long eno; //번호

    @Column(nullable = false)
    private String etitle; //제목

    @Lob
    @Column(nullable = false, length = 5000)
    private String econtent; //내용

    private LocalDateTime regDate, modDate;

    @ManyToOne(fetch = FetchType.LAZY)
    private Member member; //작성자

    @OneToMany(mappedBy = "event", cascade = CascadeType.REMOVE)
    private List<EventImage> images;

    public void changeTitle(String etitle){
        this.etitle = etitle;
    }

    public void changeContent(String econtent){
        this.econtent = econtent;
    }


}
