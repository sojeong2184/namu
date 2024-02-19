package com.durianfirst.durian.entity;


import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString(exclude = "event")
public class EventImage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long einum;

    private String uuid;

    private String imgName;

    private String path;

    @ManyToOne(fetch = FetchType.LAZY)
    private Event event;


//    @Override
//    public int compareTo(EventImage other) {
//        return this.ord - other.ord;
//    }

    public void changeEvent(Event event){

        this.event = event;
    }

//    @Override
//    public int compareTo(EventImage o) {
//        return 0;
//    }
}
