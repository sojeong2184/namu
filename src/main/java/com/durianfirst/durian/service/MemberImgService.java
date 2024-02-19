package com.durianfirst.durian.service;

import com.durianfirst.durian.dto.ImageResponseDTO;
import com.durianfirst.durian.dto.MemberImageDTO;

public interface MemberImgService {

    void upload(MemberImageDTO memberImageDTO,String mid);

    ImageResponseDTO findImage(String mid);
}
