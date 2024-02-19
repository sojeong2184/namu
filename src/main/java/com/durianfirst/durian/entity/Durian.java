package com.durianfirst.durian.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@Builder
@ToString
public class Durian {
    private String image;
    private String title;
    private String url;
}
