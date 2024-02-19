package com.durianfirst.durian.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@Builder
@ToString
public class Daangn {
    private String image;
    private String title;
    private String content;
    private String address;
    private String price;
    private String heart;
    private String url;
}
