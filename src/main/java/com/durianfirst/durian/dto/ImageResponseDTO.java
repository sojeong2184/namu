package com.durianfirst.durian.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ImageResponseDTO {

    private String url;
    private String mid;


    public ImageResponseDTO(String mid){
        this.url=getUrl();
    }



}
