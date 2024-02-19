package com.durianfirst.durian.service;

import com.durianfirst.durian.dto.ImageResponseDTO;
import com.durianfirst.durian.dto.MemberImageDTO;
import com.durianfirst.durian.entity.Member;
import com.durianfirst.durian.entity.MemberImg;
import com.durianfirst.durian.repository.MemberImgRepository;
import com.durianfirst.durian.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.UUID;
@Service
@RequiredArgsConstructor
public class MemberImgServiceImpl implements MemberImgService{

    private MemberImgRepository memberImgRepository;
    private MemberRepository memberRepository;
    private String upload;



    @Override
    public void upload(MemberImageDTO memberImageDTO, String mid) {
        Member member = memberRepository.findByMid(mid);

        MultipartFile file = memberImageDTO.getOrimgname();

        UUID uuid = UUID.randomUUID();
        String imageFileName = uuid + "_" + file.getOriginalFilename();

        File destinationFile = new File(upload + imageFileName);


        try{
            file.transferTo(destinationFile);
            MemberImg memberImg = memberImgRepository.findByMember(member);
            if(memberImg != null){
                //이미지가 존재하면 url업데이트
                memberImg.updateImg("/upload/" + imageFileName);
            }else{
                //이미지가 없으면 객체 생성 후 저장
                memberImg = new MemberImg();
                memberImg.setMember(member);
                memberImg.setMimgurl("/upload/" + imageFileName);

            }
            memberImgRepository.save(memberImg);

        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }

    @Override
    public ImageResponseDTO findImage(String mid) {
        Member member = memberRepository.findByMid(mid);
        MemberImg memberImg = memberImgRepository.findByMember(member);

        String defaultImageUrl = "/upload/anonymous.png";

        if(memberImg == null){
            return ImageResponseDTO.builder().url(defaultImageUrl).build();
        }else{
            return ImageResponseDTO.builder().url(defaultImageUrl).build();
        }
    }
}
